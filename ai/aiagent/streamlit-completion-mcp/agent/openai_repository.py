from dataclasses import dataclass
import asyncio
import json
import mcp_client
from openai import OpenAI, NOT_GIVEN
from openai.types.chat.parsed_function_tool_call import ChatCompletionMessageToolCall
from typing import Callable

MODEL = "gpt-4o-mini"

@dataclass
class CompletionResult:
    result: str
    messages: list[dict]

class OpenAiRepository:

    def __init__(self, log_write: Callable[[str], None]):
        self.client = OpenAI()
        self.confluence_mcp_client = mcp_client.MCPClient(server_script_path="../mcp-servers/build/confluence/index.js")
        self.file_mcp_client = mcp_client.MCPClient(server_script_path="../mcp-servers/build/file/index.js")
        self.os_mcp_client = mcp_client.MCPClient(server_script_path="../mcp-servers/build/os/index.js")
        self.log_write = log_write

    async def completion(
            self,
            message: str,
            confirm_callback: Callable,
            result_callback: Callable,
            system_message: str = "You are a helpful assistant.",
            completion_history: list[dict] = [],
            ) -> None:
        
        mcp_client_list = [self.confluence_mcp_client, self.file_mcp_client, self.os_mcp_client]
        mcp_client_white_list = [self.confluence_mcp_client, self.file_mcp_client]
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
            
            server_dict = server_dict | {tool.name: mcp_client for tool in tool_list}
        if len(completion_history) > 0:
            messages = completion_history + [{"role": "user", "content": message}]
        else:
            messages = [
                {"role": "system", "content": system_message},
                {"role": "user", "content": message}
            ]
        completion = self.client.chat.completions.create(
            model=MODEL,
            messages=messages,
            tools=function_list
        )

        async def call_tool(tool_call: ChatCompletionMessageToolCall) -> dict:
            tool_name = tool_call.function.name
            tool_args = json.loads(tool_call.function.arguments)
            mcp_client = server_dict[tool_name]
            tool_result = await mcp_client.call_tool(tool_name=tool_name, tool_args=tool_args)
            self.log_write({
                    "summary": "function called",
                    "name": tool_name,
                    "result": {
                        "text": tool_result
                    }
                })
            return {
                "role": "tool",
                "tool_call_id": tool_call.id,
                "content": tool_result
            }


        async def call_tools(
                # messageリスト
                messages: list[dict],
                # 確認OKなツール
                tool_call: ChatCompletionMessageToolCall | None,
                # 未処理なツールリスト
                tool_calls,
                ):
            # 確認OKなツールを実行する
            if tool_call:
                result = await call_tool(tool_call)
                messages.append(result)

            # 未処理のツールを実行
            for tool_call in tool_calls:
                tool_name = tool_call.function.name
                tool_args = json.loads(tool_call.function.arguments)
                self.log_write({
                    "summary": "function calling",
                    "name": tool_name,
                    "args": tool_args
                })
                mcp_client = server_dict[tool_name]
                if mcp_client not in mcp_client_white_list:
                    await confirm_callback(tool_name, tool_args, confirm_ok)
                    return
                
                call_tool_result = await call_tool(tool_call)
                messages.append(call_tool_result)
            
            completion = self.client.chat.completions.create(
                model=MODEL,
                messages=messages,
                tools=function_list
            )
            completion_result=completion.choices[0].message.content
            print(f"completion_result in call_tools: {completion_result}")
            messages.append({
                "role": "assistant",
                "content": completion_result,
            })
            result_callback(CompletionResult(
                result=completion_result,
                messages=messages
            ))



        while completion.choices[0].finish_reason == 'tool_calls':
            messages.append(completion.choices[0].message.__dict__)
            tool_calls = completion.choices[0].message.tool_calls
            for index, tool_call in enumerate(tool_calls):
                tool_name = tool_call.function.name
                tool_args = json.loads(tool_call.function.arguments)
                self.log_write({
                    "summary": "function calling",
                    "name": tool_name,
                    "args": tool_args
                })
                mcp_client = server_dict[tool_name]

                def confirm_ok():
                    asyncio.run(
                        call_tools(
                            messages=messages,
                            tool_call=tool_call,
                            tool_calls=tool_calls[index+1:]
                        )
                    )

                if mcp_client not in mcp_client_white_list:
                    confirm_callback(tool_name, tool_args, confirm_ok)
                    return

                
                call_tool_result = await call_tool(tool_call)
                messages.append(call_tool_result)
            
            completion = self.client.chat.completions.create(
                model=MODEL,
                messages=messages,
                tools=function_list
            )
        
        # 会話履歴の末尾に最終結果を追加 
        # TODO: 履歴をすべて管理ではなく、要約だけ管理するようにしたい
        completion_result=completion.choices[0].message.content
        messages.append({
            "role": "assistant",
            "content": completion_result,
        })
        result_callback(CompletionResult(
            result=completion_result,
            messages=messages
        ))
