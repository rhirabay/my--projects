import json
import time

import openai
from openai import NOT_GIVEN
client = openai.OpenAI()

planer_prompt = """
あなたはタスクの実行計画をするプロジェクトマネージャーです。
ユーザの指示を注意深く確認し、実施すべきタスクをリストアップしてください。
出力は以下のjson形式でjsonのみ返却してください。

[出力形式]
{
    "tasks": [
        {
            "task": "タスクの内容"
        }
    ]
}

[制約]
・１タスク１アクションとすること
　OK例）「XXXを調べてください」「調査結果を利用してYYYを実施してください」
　NG例）「XXXを調べてYYYを実施してください」
・依頼口調とすること
"""

worker_prompt = """
問い合わせ内容に応じて、指示通り回答を行ってください。

# メールの分類リストについて
以下の３つの分類を返却してください
・ログイン
・商品
・採用

# 応答事例について
## ログイン
Q. パスワードを忘れた
　A. パスワードリセットをお試しください
Q. パスワードリセットをしてもログインできない
  A. パスワードの入力を数回失敗すると一定時間ログインできなくなります。時間をおいて再度お試しください。

# その他
一般的な知識で回答してください。
"""

request = """
以下のメールに返信をしてください。

件名：アカウントにログインできません
本文：
アカウントにログインできず困っています。パスワードをリセットしても解決しませんでした。サポートをお願いします。
"""

workflow_list = """
| workflow | 説明 |
| --- | --- |
| mail | メールでの問い合わせに対する対応手順 |
| design | UIデザインに関する対応手順 |
| daily_report | 日報作成に関する対応手順 |
"""

mail_workflow = """
# 1. メールを分類する
・利用可能なメール分類を取得し、その中から適切な分類を選択してください。

# 2. 分類に対応する応答事例を取得
・メール分類に該当する応答事例を探してください。

# 3. メール返信文を作成
・応答事例を参考にして、メール返信文を作成してください。
"""

# アシスタントを生成
planer = client.beta.assistants.create(
    name="プランナー",
    instructions=planer_prompt,
    tools=[],
    model="gpt-4o-mini",
)
worker = client.beta.assistants.create(
    name="担当者",
    instructions=worker_prompt,
    tools=[],
    model="gpt-4o-mini",
)

# 呪文...
def run_assistant(
        assistant_id: str,
        thread_id: str,
        json_output: bool | None = None
) -> str:
    run = client.beta.threads.runs.create(
        thread_id=thread_id,
        assistant_id=assistant_id,
        response_format={
            'type': 'json_object',
        } if json_output else NOT_GIVEN
    )

    while True:
        # スレッドの実行結果を取得
        run = client.beta.threads.runs.retrieve(
            thread_id=thread_id,
            run_id=run.id
        )

        if run.status == 'in_progress' or run.status == 'queued':
            """
            実行中/実行前の場合は、1秒待って再実行
            """
            time.sleep(1)
        elif run.status == 'completed':
            """
            処理完了の場合は、応答を表示
            """
            response = client.beta.threads.messages.list(
                thread_id=thread_id
            )

            return response.data[0].content[0].text.value
        else:
            """
            想定外のステータスの場合は、statusとその他の情報を出力してエラーメッセージを返却
            """
            print(run.status)
            print(run)
            raise Exception(f"assistantの実行中に想定外のステータスを検知しました（ステータス={run.status}）")


# スレッドを生成
thread = client.beta.threads.create()

# 準備1. スレッドにメッセージを追加
user_message = f'''
次の問い合わせについてstep by stepで指示を出すので、各stepごとに実行してください。

問い合わせ：
{request}
'''
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=request
)

# step1. 適切なワークフローを選択する
workflow_select_prompt = f"""
以下のワークフロー一覧から適切なものを選択してください。

ワークフロー一覧：
{workflow_list}

出力形式（json）：
{{
  "workflow": "選択したワークフロー"
}}
"""
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=workflow_select_prompt
)
workflow_result_text = run_assistant(worker.id, thread.id, json_output=True)
workflow_result = json.loads(workflow_result_text)
print('■ワークフロー：\n', workflow_result)

if workflow_result['workflow'] == 'mail':
    workflow = mail_workflow
else:
    # 用意していないのでエラーに。。。
    print('選択したワークフローが未対応です。')
    exit(1)

# step2. ワークフローを渡す
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content=f"ワークフローは以下の通りです。\n\n{workflow}"
)

# step3: タスクを計画してもらう
client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content="問い合わせに回答するために必要なタスクをリストアップしてください。"
)
plan_result_text = run_assistant(planer.id, thread.id, json_output=True)
plan_result = json.loads(plan_result_text)

print('■計画:\n', plan_result)

# step4. 計画したタスクを実行する
task_result_list = []
for task in plan_result['tasks']:
    print('■指示:\n', task)
    client.beta.threads.messages.create(
        thread_id=thread.id,
        role="user",
        content=task['task']
    )
    task_result = run_assistant(
        assistant_id=worker.id,
        thread_id=thread.id
    )
    print('■結果:\n', task_result)

    task_result_list.append(task_result)

# step5. 結果をまとめる
system_prompt = f"""
以下の問い合わせに対するタスクの実行結果を「===」で結合して渡します。
問い合わせに対する回答として自然な内容になるようにまとめてください。
まとめる際は、プロフェッショナルかつ友好的な表現を心がけてください。

問い合わせ：
{request}
"""
task_result_text = '\n\n===\n\n'.join(task_result_list)
completion = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {"role": "system", "content": system_prompt},
        {"role": "user", "content": task_result_text}
    ]
)
print('■まとめ：\n', completion.choices[0].message.content)
