import streamlit as st
import time
from streamlit.components.v1 import html
from typing import Callable

def show() -> Callable[[str], None]:
    st.header("Background Task")
    log_container = st.container(key="log_container", height=500)
    log_container.empty()

    # セッションステートを初期化
    if "background_task_history" not in st.session_state:
        st.session_state.background_task_history = []

    def write(data: dict):
        # 新しいメッセージを履歴に追加
        st.session_state.background_task_history.append(data)

        with log_container:
            log_container.empty()
            for message in st.session_state.background_task_history:  # セッションに保存されたメッセージをすべて表示
                with st.chat_message("assistant"):
                    st.json(body=message, expanded=1)

    return write
