# models
EMBEDDING_MODEL = "text-embedding-3-large"
GPT_MODEL = "gpt-4o"

basedir = __file__.replace('pgvector_write.py', '')

docsFile = basedir + '.docs/diran_moreno.txt'

# docsFileをテキストで読み込み
with open(docsFile, 'r') as f:
    raw_document = f.read()

from langchain_openai import OpenAIEmbeddings
embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

##################################

from langchain_core.documents import Document
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

docs = [
    Document(
        page_content=document,
        metadata={"id": idx, "type": "slack", "channel": "test-channel"},
    ) for (idx, document) in enumerate(raw_document.split("\n")) if len(document) > 0
]

# vector_store.delete(ids=["3"])
# vector_store.search()
vector_store.add_documents(docs, ids=[doc.metadata["id"] for doc in docs])