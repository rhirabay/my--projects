import openai
client = openai.OpenAI()

# スレッドIDを標準入力から受け取る
thread_id = input("Enter thread ID: ")

messages = client.beta.threads.messages.list(thread_id=thread_id)

for idx, message in enumerate(messages.data[::-1]):
    print(f"### Message {idx+1}:")
    print(message.content[0].text.value)
    print('\n\n')
