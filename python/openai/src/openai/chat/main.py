# 環境変数の読み込み
from dotenv import load_dotenv
load_dotenv()

from openai import OpenAI
client = OpenAI()

completion = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {"role": "system", "content": "あなたのタスクは、ユーザが指示する要件にしたがってJavaコードを生成することです"},
        {"role": "user", "content": "SpringBootでHelloWorldを作成してください。ビルドツールはgradleで"}
    ]
)

print(completion.choices[0].message)
