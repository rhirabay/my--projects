
# スクリプトのディレクトリを取得
import os
script_dir = os.path.dirname(os.path.abspath(__file__))

chunk_size = 6000
over_size = 400
target_file = 'meeting_saved_closed_caption_4bu'

# meeting_saved_closed_caption_4bu.txtを読み込む
with open(f'{script_dir}/{target_file}.txt', 'r') as f:
    lines = f.readlines()

# 会議メモのリストを作成
meeting_memo_list = []
memo = ''

for line in lines:
    # [始まりだったら
    if line.startswith('['):
        # 既にメモがある場合はリストに追加
        if memo:
            meeting_memo_list.append(memo)
            memo = ''

    memo += line
if memo:
    meeting_memo_list.append(memo)

# 2000文字ごとに集約（ただし、200文字程度の重複をもたせる）
meeting_memo_list_aggregated = []
memo = ''
memo_over = ''
for meeting_memo in meeting_memo_list:
    memo += meeting_memo
    memo_over += meeting_memo
    if len(memo) > chunk_size:
        memo_over = memo
        memo = ''

    if len(memo_over) > chunk_size + over_size:
        meeting_memo_list_aggregated.append(memo_over)
        memo_over = ''
if memo_over:
    meeting_memo_list_aggregated.append(memo_over)
if memo:
    meeting_memo_list_aggregated.append(memo)

print(len(meeting_memo_list_aggregated))

# ファイルに書き出す
for (idx, meeting_memo) in enumerate(meeting_memo_list_aggregated):
    with open(f'{script_dir}/.work/split/{target_file}_{idx}.txt', 'w') as f:
        f.write(meeting_memo)
