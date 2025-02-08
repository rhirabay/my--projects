from presentation import chat
from presentation import logs
import streamlit as st
import time
import asyncio

from dotenv import load_dotenv
load_dotenv(dotenv_path='../.env')

import sys, os
sys.path.append('.')
from openai_repository import OpenAiRepository

st.set_page_config(layout="wide")

# カスタムCSSを追加
st.markdown("<style>body {font-size: 75%;}</style>", unsafe_allow_html=True)

# ユーザー入力
user_input = st.chat_input("Enter your message...")
# レイアウトを2カラムに分割
col1, col2 = st.columns(2)

async def main():
    # 右カラム: ログ出力
    with col2:
        log_write_callback = logs.show()

    openai_repository = OpenAiRepository(log_write=log_write_callback)

    # 左カラム: チャット
    with col1:
        result_write_callback = await chat.show(
            user_input=user_input,
            openai_repository=openai_repository
        )

asyncio.run(main())