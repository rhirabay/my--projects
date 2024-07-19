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
files = os.listdir(f'{script_dir}/.work/each')
files = [f for f in files if f.startswith(f'{target_file}_')]
# 並び替え
files.sort(key=lambda x: int(x.split('_')[-1].split('.')[0]))

user_prompt = ''
for i, file in enumerate(files):
    with open(f'{script_dir}/.work/each/{file}', 'r') as f:
        meeting_memo = f.read()
    user_prompt += f'[議事録 {i}]\n\n{meeting_memo}\n\n'

# プロンプトを読み込む
with open(f'{script_dir}/prompt_2.txt', 'r') as f:
    system_prompt = f.read()

completion = client.chat.completions.create(
    model="gpt-4o",
    messages=[
        {"role": "system", "content": system_prompt},
        {"role": "user", "content": user_prompt}
    ]
)

result = completion.choices[0].message.content
with open(f'{script_dir}/.work/all/{target_file}.txt', 'w') as f:
    f.write(result)
