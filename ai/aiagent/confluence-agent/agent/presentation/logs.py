import streamlit as st
import time
from streamlit.components.v1 import html
from typing import Callable

def show() -> Callable[[str], None]:
    st.header("Logs")
    log_container = st.container(height=500, key="log_container")

    def write(message: str):
        with log_container:
            st.write(message)

    return write
    
    # if 'messages' not in st.session_state: 
    #     st.session_state.messages = []

    # with log_container:
    #     for message in st.session_state.messages:
    #         st.write(message)

    #     for i in range(100):
    #         message = f"ログエントリ {i+1}: 処理内容"
    #         st.session_state.messages.append(message)
    #         st.write(message)
