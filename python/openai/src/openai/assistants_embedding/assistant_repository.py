import json
import sys, os, time
import textwrap

from openai import OpenAI

from src.openai.assistants_embedding.functions.function_base import FunctionBase
from src.openai.assistants_embedding.functions.write_memo_function import WriteMemoFunction
from src.openai.assistants_embedding.knowledge_repository import KnowledgeRepository

sys.path.append('.')


class AssistantRepository:
    """
    Assistantの基本操作をWrapしてシームレスに扱えるようにするクラス
    やること
    ・複雑な手続きを隠蔽
    やらないこと
    ・Assistant自体の管理（作成・更新）
    """

    def __init__(self):
        self.client = OpenAI()
        self.knowledge_repository = KnowledgeRepository()

    def create_thread(self) -> str:
        thread = self.client.beta.threads.create()
        print(f'thread created: {thread.id}')
        return thread.id

    def add_message(self,
                    message: str,
                    thread_id: str):
        """
        指定されたスレッドにメッセージを追加する
        """
        self.client.beta.threads.messages.create(
            thread_id=thread_id,
            role="user",
            content=message
        )

    def search_knowledge(self, message) -> str:
        knowledge_list = self.knowledge_repository.search(message, min_similarity=0)
        if len(knowledge_list) == 0:
            return ""

        knowledge = '\n\n'.join(knowledge_list)

        return textwrap.dedent(f"""
        [参考情報]
        必要に応じて以下の情報を参考にしてください
        
        {knowledge}
        """)

    def add_message_with_knowledge(self,
                                   message: str,
                                   thread_id: str):
        knowledge = self.search_knowledge(message)
        self.add_message(message=f'{message}\n\n{knowledge}', thread_id=thread_id)

    def run(self,
            thread_id: str,
            assistant_id: str,
            functions: [FunctionBase]) -> str:
        run = self.client.beta.threads.runs.create(
            thread_id=thread_id,
            assistant_id=assistant_id,
        )

        check_count = 0
        sleep_seconds = 1
        while True:
            # スレッドの実行結果を取得
            run = self.client.beta.threads.runs.retrieve(
                thread_id=thread_id,
                run_id=run.id
            )
            print(f'run.status: {run.status}')

            if run.status == 'in_progress' or run.status == 'queued':
                """
                実行中/実行前の場合は、1秒待って再実行
                """
                if check_count * sleep_seconds > 60:
                    # 1分を超えて処理中の場合はキャンセルしてエラー終了
                    self.client.beta.threads.runs.cancel(thread_id=thread_id, run_id=run.id)
                    return "Assistantの実行がタイムアウトしました"
                time.sleep(1)
                check_count += 1
            elif run.status == 'completed':
                """
                処理完了の場合は、応答を表示して終了
                """
                response = self.client.beta.threads.messages.list(
                    thread_id=thread_id
                )

                return response.data[0].content[0].text.value
            elif run.status == 'requires_action':
                """
                toolの実行が必要な場合は、指定されたnameのツールを実行
                """
                check_count = 0  # 初期化
                self.execute_action(
                    run=run,
                    thread_id=thread_id,
                    functions=functions
                )
            else:
                """
                想定外のステータスの場合は、statusとその他の情報を出力してエラーメッセージを返却
                """
                print(run.status)
                print(run)
                return f"assistantの実行中に想定外のステータスを検知しました（ステータス={run.status}）"

    def execute_action(self,
                       run,
                       thread_id: str,
                       functions: [FunctionBase]):
        tool_outputs = []
        calling_tool_list = run.required_action.submit_tool_outputs.tool_calls
        for calling_tool in calling_tool_list:
            calling_function = calling_tool.function
            for function in functions:
                if function.summary()["name"] == calling_function.name:
                    # function実行
                    output = function.call(calling_function.arguments)
                    tool_outputs.append({
                        "tool_call_id": calling_tool.id,
                        "output": output
                    })
                    break
        if len(tool_outputs) == 0:
            print('実行対象のツールが見つかりませんでした')
            print(run.status)
            print(run)

        self.client.beta.threads.runs.submit_tool_outputs_and_poll(
            thread_id=thread_id,
            run_id=run.id,
            tool_outputs=tool_outputs
        )

    def classify(self,
                 system_message: str,
                 user_message: str) -> str:
        chat_completion = self.client.chat.completions.create(
            messages=[
                {
                    "role": "system",
                    "content": system_message,
                },
                {
                    "role": "user",
                    "content": user_message,
                }
            ],
            model="gpt-4o",
            temperature=0.1,
        )

        result_message = chat_completion.choices[0].message.content
        print(f'### 分類の結果: {result_message}\n\n')
        return result_message


