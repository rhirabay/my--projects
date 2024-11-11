import json
from abc import ABC, abstractmethod
from openai.types.shared_params import FunctionDefinition


class FunctionBase(ABC):
    def to_dict(self, arguments: str) -> dict:
        return json.loads(arguments)

    @abstractmethod
    def summary(self) -> FunctionDefinition:
        pass

    def call(self, arguments: str) -> str:
        try:
            print(f'tool [{self.summary()["name"]}] running with arguments -> {arguments}')
            arguments_dict = self.to_dict(arguments)
            output = self.execute(arguments_dict)

            return output
        except Exception as e:
            print(f'Assistant tool error -> {e}')
            return '処理に失敗しました。'

    @abstractmethod
    def execute(self, arguments: dict) -> str:
        pass
