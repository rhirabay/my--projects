# 環境変数の読み込み
from dotenv import load_dotenv
load_dotenv()

from openai import OpenAI
client = OpenAI()

# スクリプトのディレクトリを取得
import os
script_dir = os.path.dirname(os.path.abspath(__file__))

chunk_size = 6000
over_size = 400
target_file = 'meeting_saved_closed_caption_4bu'

# .work/split配下のファイルを一覧で取得
files = os.listdir(f'{script_dir}/.work/split')
files = [f for f in files if f.startswith(f'{target_file}_')]

# プロンプトを読み込む
with open(f'{script_dir}/prompt_1.txt', 'r') as f:
    prompt = f.read()

# ファイルを読み込む
for file in files:
    print(file)
    with open(f'{script_dir}/.work/split/{file}', 'r') as f:
        meeting_memo = f.read()

    completion = client.chat.completions.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": prompt},
            {"role": "user", "content": f'■Zoom書き起こし\n\n{meeting_memo}'}
        ]
    )

    result = completion.choices[0].message.content

    with open(f'{script_dir}/.work/each/{file}', 'w') as f:
        f.write(result)
