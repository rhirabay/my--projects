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
collection = client.get_collection(
    name="my_collection",
    embedding_function=openai_ef
)

query = ('SEIJIとはどのような人物ですか？')
embeddings = openai_ef([query])
result = collection.query(embeddings)
print(result)

