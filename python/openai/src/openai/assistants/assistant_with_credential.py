import json
import time

import openai
client = openai.OpenAI()

import sys
sys.path.append('.')
from src.openai.assistants.masking_filter import extract_and_mask, embed

# アシスタントを生成
assistant = client.beta.assistants.create(
    name="アシスタント",
    instructions="あなたは優秀なAIアシスタントです。ユーザの指示に注意深く従い、回答してください。",
    tools=[{
        "type": "function",
        "function": {
            "name": "register_bank_account",
            "description": "銀行口座を登録する",
            "parameters": {
                "type": "object",
                "properties": {
                    "bankAccountNumber": {
                        "type": "string",
                        "description": "銀行口座番号。例１：1234567。例２（マスキングあり）:{bankAccountNumber_1}"
                    }
                },
                "required": ["bankAccountNumber"]
            }
        }
    }],
    model="gpt-4o",
)

# スレッドを生成
thread = client.beta.threads.create()

user_message, extract_values = extract_and_mask("口座登録をしてください。口座番号は「1234567」です。", extract_values={})

# スレッドにメッセージを追加
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=user_message
)

def register_bank_account(arguments) -> str:
    bank_account_number = arguments['bankAccountNumber']
    result_message = f'function: 口座登録を実施しました {bank_account_number}'
    return result_message


def tool_action(run, thread_id: str, extract_values: dict) -> dict:
    _extract_values = extract_values.copy()
    tool_outputs = []
    # 実行対象のツールを取得
    calling_tool_list = run.required_action.submit_tool_outputs.tool_calls
    for calling_tool in calling_tool_list:
        calling_function = calling_tool.function
        if calling_function.name == 'register_bank_account':
            # ツール実行前にマスキングを戻す
            arguments = embed(calling_function.arguments, _extract_values)
            print(f'{calling_function.name} processing. arguments from ai: {calling_function.arguments}, arguments after unmask: {arguments}')
            # function実行
            output = register_bank_account(json.loads(arguments))
            # 実行結果をマスキング
            _output, _extract_values = extract_and_mask(output, extract_values=_extract_values.copy())
            print(f'{calling_function.name} completed. output from tool: {output}, output after mask: {_output}')
            tool_outputs.append({
                "tool_call_id": calling_tool.id,
                "output": _output
            })
            break

    client.beta.threads.runs.submit_tool_outputs_and_poll(
        thread_id=thread_id,
        run_id=run.id,
        tool_outputs=tool_outputs
    )
    return _extract_values


def run_assistant(assistant_id: str, extract_values: dict) -> str:
    _extract_values = extract_values.copy()
    run = client.beta.threads.runs.create(
        thread_id=thread.id,
        assistant_id=assistant_id
    )

    run = client.beta.threads.runs.retrieve(
        thread_id=thread.id,
        run_id=run.id
    )

    while True:
        # スレッドの実行結果を取得
        run = client.beta.threads.runs.retrieve(
            thread_id=thread.id,
            run_id=run.id
        )

        if run.status == 'in_progress' or run.status == 'queued':
            """
            実行中/実行前の場合は、2秒待って再実行
            """
            time.sleep(2)
        elif run.status == 'completed':
            """
            処理完了の場合は、応答を表示
            """
            response = client.beta.threads.messages.list(
                thread_id=thread.id
            )

            return embed(response.data[0].content[0].text.value, _extract_values)
        elif run.status == 'requires_action':
            """
            toolの実行が必要な場合は、指定されたnameのツールを実行
            """
            _extract_values = tool_action(run, thread.id, extract_values=_extract_values)
        else:
            """
            想定外のステータスの場合は、statusとその他の情報を出力してエラーメッセージを返却
            """
            print(run.status)
            print(run)
            raise Exception(f"assistantの実行中に想定外のステータスを検知しました（ステータス={run.status}）")


result_message = run_assistant(assistant.id, extract_values)
print(f'### Message result')
print(f'{result_message}\n')

messages = client.beta.threads.messages.list(
    thread_id=thread.id,
).data
messages.reverse()
for index, message in enumerate(messages):
    print(f'### Message {index + 1}')
    print(f'{message.role}: {message.content[0].text.value}\n')