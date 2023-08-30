# load env
from dotenv import load_dotenv
load_dotenv(dotenv_path="../../../../.env")

# load index
from llama_index import StorageContext, load_index_from_storage
storage_context = StorageContext.from_defaults(persist_dir="../../.data")
index = load_index_from_storage(storage_context)

# create query engine
query_engine = index.as_query_engine(
    streaming=True,
)

# query
streaming_response = query_engine.query(
    "アンナの生い立ちは？100字程度で。",
)
streaming_response.print_response_stream()
