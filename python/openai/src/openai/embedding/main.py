# 環境変数の読み込み
from dotenv import load_dotenv
load_dotenv()

from scipy import spatial
import numpy as np

# models
EMBEDDING_MODEL = "text-embedding-ada-002"
GPT_MODEL = "gpt-3.5-turbo"


query = 'ディラン・モレノの経歴は？'


import pandas as pd


basedir = __file__.replace('main.py', '')
dataFile = basedir + '.data/sample.csv'

docsFile = basedir + '.docs/diran_moreno.txt'


import openai
client = openai.OpenAI()

# openaiのEmbeddingを実行してdateFrameに格納（dateFrame生成も）


def get_embedding(_doc_content):
    return client.embeddings.create(input=[_doc_content], model=EMBEDDING_MODEL).data[0].embedding


# CSVからベクトルデータを読み込み
df = pd.read_csv(dataFile)
# embeddingを配列に変換
df['embedding'] = df['embedding'].apply(eval).apply(np.array)


# search function
def strings_ranked_by_relatedness(
        query: str,
        df: pd.DataFrame,
        relatedness_fn=lambda x, y: 1 - spatial.distance.cosine(x, y),
        top_n: int = 100
) -> tuple[list[str], list[float]]:
    """Returns a list of strings and relatednesses, sorted from most related to least."""
    query_embedding_response = openai.embeddings.create(
        model=EMBEDDING_MODEL,
        input=query,
    )
    query_embedding = query_embedding_response.data[0].embedding
    strings_and_relatednesses = [
        (row["text"], relatedness_fn(query_embedding, row["embedding"]))
        for i, row in df.iterrows()
    ]
    strings_and_relatednesses.sort(key=lambda x: x[1], reverse=True)
    strings, relatednesses = zip(*strings_and_relatednesses)
    return strings[:top_n], relatednesses[:top_n]



# examples
text_list, _ = strings_ranked_by_relatedness(query, df, top_n=5)


# 所定のtoken数（文字数で代用）まで文字列を結合
ref_doc = ''
for text in text_list:
    if len(ref_doc) < 1000:
        ref_doc += text
    else:
        break


messages = [
    {"role": "system", "content": "ユーザの質問に対して、簡潔に日本語で回答してください。"},
    {"role": "system", "content": f"""
ただし、質問の回答は以下の文章に基づいて回答すること。

文章
-----
{ref_doc}
-----
"""},
    {"role": "user", "content": query},
]
response = openai.chat.completions.create(
    model=GPT_MODEL,
    messages=messages,
    temperature=0
)
response_message = response.choices[0].message.content
print(response_message)