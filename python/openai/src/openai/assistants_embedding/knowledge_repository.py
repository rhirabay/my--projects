import uuid

from langchain_openai import OpenAIEmbeddings

from langchain_core.documents import Document
from langchain_postgres import PGVector


class KnowledgeRepository:
    def __init__(self):
        self.embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

        connection = "postgresql+psycopg://postgres:password@localhost:5432/mydb"  # Uses psycopg3!
        self.vector_store = PGVector(
            embeddings=self.embeddings,
            connection=connection,
            use_jsonb=True,
        )

    def add_document(self, document: str):
        docs = [
            Document(
                page_content=document,
                metadata={"id": str(uuid.UUID)},
            )
        ]
        self.vector_store.add_documents(docs, ids=[doc.metadata["id"] for doc in docs])

    def search(self, query, limit: int = 5, min_similarity: float = 0.5):
        result = self.vector_store.similarity_search_with_score(
            query, k=limit
        )

        for doc, score in result:
            print(f"### Score: {score}\nDocument: {doc.page_content}")

        return [doc.page_content for doc, score in result if score >= min_similarity]
