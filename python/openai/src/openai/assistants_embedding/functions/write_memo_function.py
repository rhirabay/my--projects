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
            ユーザから「覚えて」というリクエストを受け取った際に内容を記録しておくための機能。
            """),
            "parameters": {
                "type": "object",
                "properties": {
                    "memo": {
                        "type": "string",
                        "description": textwrap.dedent("""
                        記録する内容。
                        どのような条件で、どのようなアウトプットが求められるのか会話の流れも考慮して詳述にまとめること。
                        """)
                    }
                },
                "required": ["memo"]
            }
        }

    def execute(self, arguments: dict) -> str:
        self.knowledge_repository.add_document(arguments['memo'])

        return "登録に成功しました"
