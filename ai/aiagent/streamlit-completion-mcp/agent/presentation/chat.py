import traceback
import streamlit as st
from typing import Callable
from openai_repository import OpenAiRepository, CompletionResult

async def show(user_input, openai_repository: OpenAiRepository) -> None:
    st.header("Chat")


    if "chat_log" not in st.session_state:
        st.session_state.chat_log = []

    chat_container = st.container(key="chat_container", height=500)
    chat_container.empty()

    # チャット履歴表示
    def show_chat_log():
        with chat_container:
            for chat in st.session_state.chat_log:
                # contentが空の場合は表示しない
                if not chat["content"]:
                    continue

                if chat['role'] == 'user':
                    with st.chat_message('user'):
                        st.write(chat["content"])
                elif chat['role'] == 'assistant':
                    with st.chat_message('assistant'):
                        st.write(chat["content"])
    show_chat_log()


    if user_input:
        # ユーザーのメッセージを表示
        with chat_container:
            with st.chat_message("user"):
                st.write(user_input)

        def show_tool_confirm_button(tool_name, args: dict[str, str], on_button_click: Callable):
            with chat_container:
                with st.chat_message("assistant"):
                    st.write(f'以下のツールを実行します。\nツール名: {tool_name}\nパラメータ: {args}')
                st.button('承認', type='primary', on_click=on_button_click)

        def show_result_callback(result: CompletionResult):
            # ログに追加
            st.session_state.chat_log = result.messages
            show_chat_log()

        await openai_repository.completion(
            message=user_input,
            completion_history=st.session_state.chat_log,
            confirm_callback=show_tool_confirm_button,
            result_callback=show_result_callback,
        )

    return None