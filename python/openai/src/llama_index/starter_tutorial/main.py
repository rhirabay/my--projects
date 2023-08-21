# -----　インデックスの読み込み -----
from llama_index import StorageContext, load_index_from_storage

storage_context = StorageContext.from_defaults(persist_dir="../.data")
saved_index = load_index_from_storage(storage_context=storage_context)

response_from_saved_index = saved_index.as_query_engine().query("""
ディランの生い立ちを簡潔に教えてください。
""")
print(response_from_saved_index)
