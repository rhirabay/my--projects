import textwrap
from src.openai.assistants_embedding.functions.function_base import FunctionBase
from src.openai.assistants_embedding.knowledge_repository import KnowledgeRepository


class WriteMemoFunction(FunctionBase):
    def __init__(self):
        self.knowledge_repository = KnowledgeRepository()

    def summary(self) -> dict:
        return {
            "name": "write_memo",
            "description": textwrap.dedent("""
            ユーザから記憶しておく依頼を受け取った際に内容を記録しておくための機能。
            会話履歴からのみ情報を整理し、簡潔に記載すること（背景や目的、結果、関連情報など）。
            例１）XXXの場合、YYYを利用すること
            例２）XXXに関する情報は、YYYを参照すること
            """),
            "parameters": {
                "type": "object",
                "properties": {
                    "tags": {
                        "type": "array",
                        "items": {
                            "type": "string",
                            "description": textwrap.dedent("""
                            メモを検索する時に役立つタグ
                            """)
                        }
                    },
                    "memo": {
                        "type": "string",
                        "description": textwrap.dedent("""
                        メモ本体。
                        """)
                    }
                },
                "required": ["memo", "tags"]
            }
        }

    def execute(self, arguments: dict) -> str:
        self.knowledge_repository.add_document(arguments['memo'])

        return "登録に成功しました"
