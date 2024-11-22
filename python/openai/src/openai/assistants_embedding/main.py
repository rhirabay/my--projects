import textwrap
import sys, os

from openai import OpenAI

sys.path.append('.')
from src.openai.assistants_embedding.assistant_repository import AssistantRepository
from src.openai.assistants_embedding.functions.write_memo_function import WriteMemoFunction

import dotenv
env_file = f'{os.path.dirname(__file__)}.env'
dotenv.load_dotenv(env_file)

client = OpenAI()
functions = [
    # WriteMemoFunction()
]
assistant_prompt = textwrap.dedent("""
あなたは優秀なITエンジニアです。

[制約]
・80文字以内に改行を入れること。
""")


def create_assistant():
    tools = [{
        "type": "function",
        "function": function.summary()
    } for function in functions]

    tools.append({
        "type": "code_interpreter"
    })

    # アシスタントを作成
    assistant = client.beta.assistants.create(
        instructions=assistant_prompt,
        model="gpt-4o",
        tools=tools
    )
    print(f"Assistant created: {assistant.id}")


def update_assistant(assistant_id: str):
    tools = [{
        "type": "function",
        "function": function.summary()
    } for function in functions]

    tools.append({
        "type": "code_interpreter"
    })

    # アシスタントを更新
    assistant = client.beta.assistants.update(
        assistant_id=assistant_id,
        instructions=assistant_prompt,
        model="gpt-4o",
        tools=tools
    )

    print(f"Assistant updated: {assistant.id}")


assistant_id = "asst_RQRQ3Cv1w5vo0jDXVcJnkx82"
thread_id = ""
if __name__ == "__main__":
    if assistant_id is None or len(assistant_id) == 0:
        create_assistant()
        exit(0)
    update_assistant(assistant_id=assistant_id)

    assistant_repository = AssistantRepository()
    if thread_id is None or len(thread_id) == 0:
        thread_id = assistant_repository.create_thread()

    assistant_repository.add_message_with_knowledge(message=textwrap.dedent("""
    Spring Boot 3.3の機能についてのブログ知ってる？
    """), thread_id=thread_id)

    # assistant_repository.add_message_with_knowledge(message=textwrap.dedent("""
    # じゃあ、このブログ覚えておいて！
    # https://hirabay.net/?p=715
    # """), thread_id=thread_id)

    result = assistant_repository.run(thread_id=thread_id, assistant_id=assistant_id, functions=functions)
    print(result)
