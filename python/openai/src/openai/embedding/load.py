# 環境変数の読み込み
from dotenv import load_dotenv
load_dotenv()


# models
EMBEDDING_MODEL = "text-embedding-ada-002"
GPT_MODEL = "gpt-3.5-turbo"


import pandas as pd

basedir = __file__.replace('load.py', '')
dataFile = basedir + '.data/sample.csv'

docsFile = basedir + '.docs/diran_moreno.txt'

# docsFileをテキストで読み込み
with open(docsFile, 'r') as f:
    docs = f.read()


# docsを改行で分割＆空行を削除
doc_contents = [doc for doc in docs.split('\n') if doc != '']


import openai
client = openai.OpenAI()


def get_embedding(_doc_content):
    return client.embeddings.create(input=[_doc_content], model=EMBEDDING_MODEL).data[0].embedding


df = pd.DataFrame()
df['text'] = doc_contents
df['embedding'] = df['text'].apply(get_embedding)

df.to_csv(dataFile, index=False)
