# 環境変数の読み込み
from os import environ

from dotenv import load_dotenv
load_dotenv()

# import
from langchain_chroma import Chroma
import chromadb
from langchain_community.document_loaders import TextLoader
from langchain_community.embeddings.sentence_transformer import (
    SentenceTransformerEmbeddings,
)
from langchain_text_splitters import CharacterTextSplitter
from langchain_openai import OpenAIEmbeddings


# create the open-source embedding function
import chromadb.utils.embedding_functions as embedding_functions
openai_ef = embedding_functions.OpenAIEmbeddingFunction(
    api_key=environ.get("OPENAI_API_KEY"),
    model_name="text-embedding-3-small"
)

client = chromadb.PersistentClient(path=".chroma")

client.delete_collection(name="my_collection")
# 不要なディレクトリを削除


collection = client.create_collection(
    name="my_collection",
    embedding_function=openai_ef,
    metadata={"hnsw:space": "cosine"} # コサイン類似度
)
# Load the document
loader = TextLoader('doc.txt')
document = loader.load()

# Split the document into sentences
splitter = CharacterTextSplitter(chunk_size=200)
documents = splitter.split_documents(document)
print('documents: ')
print(documents)
sentences = [doc.page_content for doc in documents]

# Embed the sentences using the OpenAIEmbeddingFunction
embeddings = openai_ef(sentences)
print('embeddings: ')
print(embeddings)

# Save the embeddings to the collection
for sentence, embedding in zip(sentences, embeddings):
    collection.upsert(sentence, embedding)

