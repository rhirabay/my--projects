import streamlit as st
from typing import Callable
from openai_repository import OpenAiRepository

async def show(openai_repository: OpenAiRepository) -> Callable[[str], None]:
    st.header("Chat")
    if "chat_log" not in st.session_state:
        st.session_state.chat_log = []
    
    # ユーザー入力
    user_input = st.chat_input("Enter your message...")

    # チャット履歴表示
    for chat in st.session_state.chat_log:
        with st.chat_message(chat["role"]):
            st.write(chat["content"])

    if user_input:
        # ユーザーのメッセージを表示
        with st.chat_message("user"):
            st.write(user_input)
        result = await openai_repository.completion(user_input)

        with st.chat_message("assistant"):
            st.write(result)
        
        # ログに追加
        st.session_state.chat_log.append({"role": "user", "content": user_input})
        st.session_state.chat_log.append({"role": "assistant", "content": result})


    def write_result(message: str, button: bool = False):
        if button:
            if st.button(message):
                st.write(f"Button clicked: {message}")
        else:
            with st.chat_message("assistant"):
                st.write(message)
            st.session_state.chat_log.append({"role": "assistant", "content": message})

    return write_result