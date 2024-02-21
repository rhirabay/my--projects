# 環境変数の読み込み
from dotenv import load_dotenv
load_dotenv()

import openai
client = openai.OpenAI()

# アシスタントを生成
assistant = client.beta.assistants.create(
    name="サンプル",
    instructions="あなたはJavaコーディングの優秀なアシスタントです",
    tools=[],
    model="gpt-4-turbo-preview"
)

# スレッドを生成
thread = client.beta.threads.create()

# スレッドにメッセージを追加
message = client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content="'Hello world 'と出力するコードを生成して"
)

# スレッドを実行
run = client.beta.threads.runs.create(
    thread_id=thread.id,
    assistant_id=assistant.id,
    # instructions="Please address the user as Jane Doe. The user has a premium account."
)

# スレッドの実行結果を取得
run = client.beta.threads.runs.retrieve(
    thread_id=thread.id,
    run_id=run.id
)
print(run)