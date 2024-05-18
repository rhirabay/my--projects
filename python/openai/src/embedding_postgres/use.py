import sqlalchemy as sa
import pandas as pd
import numpy as np
from scipy import spatial

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

sql_query="""
select * from embeddings where 1 = 2;
"""
df = pd.read_sql(sql=sql_query, con=engine)
print('df: ')
print(df.empty)
# dfがからかどうかチェックする

df['embedding'] = df['embedding'].apply(eval).apply(np.array)
print(df)

# ***********************************:
# *   利用
# ***********************************:

# models
EMBEDDING_MODEL = "text-embedding-ada-002"
GPT_MODEL = "gpt-4o"

query = 'ディラン・モレノの経歴は？'

import openai
client = openai.OpenAI()

def get_embedding(_doc_content):
    return client.embeddings.create(input=[_doc_content], model=EMBEDDING_MODEL).data[0].embedding

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