# models
EMBEDDING_MODEL = "text-embedding-3-small"
GPT_MODEL = "gpt-3.5-turbo"


import pandas as pd

basedir = __file__.replace('create.py', '')
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
df['source'] = df['text'].apply(lambda it: docsFile)
df['embedding'] = df['embedding'].apply(lambda x: str(list(x)))
print(df)

# postgresへ保存
import sqlalchemy as sa
engine = sa.create_engine(
    sa.engine.url.URL.create(
        drivername="postgresql", # or postgresql
        username="postgres",  # e.g. "my-database-user"
        password='password',  # e.g. "my-database-password"
        host='localhost',  # e.g. "127.0.0.1"
        port=5432,  # e.g. 3306
        database='mydb',  # e.g. "my-database-name"
    )
)
df.to_sql("embeddings", con=engine, if_exists='append', index=False)