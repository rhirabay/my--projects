import asyncio
import json
import mcp_client
from openai import OpenAI, NOT_GIVEN
from typing import Callable

MODEL = "gpt-4o-mini"

class OpenAiRepository:

    def __init__(self, log_write: Callable[[str], None]):
        self.client = OpenAI()
        self.confluence_mcp_client = mcp_client.MCPClient(server_script_path="../mcp-servers/build/confluence/index.js")
        self.file_mcp_client = mcp_client.MCPClient(server_script_path="../mcp-servers/build/file/index.js")
        self.log_write = log_write

    async def completion(
            self,
            message: str,
            ) -> str:
        
        mcp_client_list = [self.confluence_mcp_client, self.file_mcp_client]
        function_list = []
        server_dict = {} # 逆引き用に辞書を作成
        for mcp_client in mcp_client_list:
            tool_list = await mcp_client.list_tools()
            function_list.extend([{
                "type": "function",
                "function": {
                    "name": tool.name,
                    "description": tool.description,
                    "parameters": tool.inputSchema
                }
            } for tool in tool_list])
            print(f"function_list: {function_list}")
            
            server_dict = server_dict | {tool.name: mcp_client for tool in tool_list}

        messages = [
            {"role": "developer", "content": "You are a helpful assistant."},
            {"role": "user", "content": message}
        ]
        completion = self.client.chat.completions.create(
            model=MODEL,
            messages=messages,
            tools=function_list
        )

        while completion.choices[0].finish_reason == 'tool_calls':
            messages.append(completion.choices[0].message)
            tool_calls = completion.choices[0].message.tool_calls
            for tool_call in tool_calls:
                tool_name = tool_call.function.name
                tool_args = json.loads(tool_call.function.arguments)
                self.log_write(f"[function calling] name:{tool_name}, args: {tool_args}")

                mcp_client = server_dict[tool_name]
                tool_result = await mcp_client.call_tool(tool_name=tool_name, tool_args=tool_args)
                self.log_write(f"[function result] {tool_result}")
                messages.append({
                    "role": "tool",
                    "tool_call_id": tool_call.id,
                    "content": tool_result
                })
            
            completion = self.client.chat.completions.create(
                model=MODEL,
                messages=messages,
                tools=function_list
            )
        
        return completion.choices[0].message.content
