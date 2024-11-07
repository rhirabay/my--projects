import time

import openai
client = openai.OpenAI()

# アシスタントを生成
member = client.beta.assistants.create(
    name="しりとりメンバー",
    instructions="あなたは日本語のエキスパートです。単語のみ回答すること。",
    tools=[],
    model="gpt-4o",
)

judgement = client.beta.assistants.create(
    name="しりとり審判",
    instructions="""
    あなたはしりとりの審判です。
    しりとりのルールに則り、しりとりが継続できるか判定してください。
    判定結果は「OK」もしくは「NG」で回答してください。
    「ん」で終わる単語が出たら判定NGです。
    例）りんご　→　OK
    例）リモコン　→　NG
    """,
    tools=[],
    model="gpt-4o",
)

# スレッドを生成
thread = client.beta.threads.create()

# スレッドにメッセージを追加
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content="しりとりを始めます。最初の単語は「しりとり」です。"
)


def run_assistant(assistant_id: str) -> str:
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
        print(f'run.status: {run.status}')

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

            return response.data[0].content[0].text.value
        elif run.status == 'requires_action':
            """
            toolの実行が必要な場合は、指定されたnameのツールを実行
            """
            break  # TODO: 必要になったら実装
        else:
            """
            想定外のステータスの場合は、statusとその他の情報を出力してエラーメッセージを返却
            """
            print(run.status)
            print(run)
            raise Exception(f"assistantの実行中に想定外のステータスを検知しました（ステータス={run.status}）")


# 10回ループ
for i in range(10):
    answer = run_assistant(member.id)
    print(f"しりとりメンバー: {answer}")
    client.beta.threads.messages.create(
        thread_id=thread.id,
        role="user",
        content="しりとりが継続可能か判定して。"
    )
    judge = run_assistant(judgement.id)
    print(f"しりとり判定: {judge}")
    if judge != "OK":
        print("しりとりが終了しました。")
        break

    client.beta.threads.messages.create(
        thread_id=thread.id,
        role="user",
        content="しりとりを続けて。"
    )
