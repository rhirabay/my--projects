# models
EMBEDDING_MODEL = "text-embedding-3-large"
GPT_MODEL = "gpt-4o"


from langchain_openai import OpenAIEmbeddings
embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

##################################

from langchain_core.documents import Document
from langchain_postgres import PGVector
from langchain_postgres.vectorstores import PGVector

# See docker command above to launch a postgres instance with pgvector enabled.
connection = "postgresql+psycopg://postgres:password@localhost:5432/mydb"  # Uses psycopg3!
collection_name = "my_conn"

vector_store = PGVector(
    embeddings=embeddings,
    collection_name=collection_name,
    connection=connection,
    use_jsonb=True,
)

results = vector_store.similarity_search_with_score(
    "ブエノスアイレス", k=3, filter={"type": "slack"}
)
for doc, score in results:
    print(f"* score: {score} -> {doc.page_content} ")