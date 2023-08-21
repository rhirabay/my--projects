from langchain.llms import OpenAI
from langchain.callbacks import get_openai_callback

llm = OpenAI(n=2, best_of=2)

with get_openai_callback() as cb:
    result = llm("なにか冗談を言って")
    print(cb)
