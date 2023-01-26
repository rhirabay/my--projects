from locust import task, constant_throughput, HttpUser
from locust import events
import uuid
import logging

# クラス名は任意（HttpUser）を継承する
class SampleUser(HttpUser):
    # API(シナリオ)ごとに@taskを付与したメソッドを定義する
    # @taskの引数には負荷をかける比重（この実装だとget:post = 2:1の割合でAPIコールする）
    # @task(2)
    # def get_request(self):
    #     value = "value"
    #     self.client.get(f"/sample?key={value}", context={"requestId": str(uuid.uuid4())})

    # @task(1)
    # def post_request(self):
    #     self.client.post("/sample", json={"key": "value"})

    @task(1)
    def error(self):
        # listenerでリクエスト・レスポンスの紐づけをするためにcontextを設定を設定しておく
        self.client.get("/ng", context={"requestId": str(uuid.uuid4())})

    # 1秒間の最大実行数を指定
    # 1に指定しておけばスレッド数以上の性能がでないので、負荷をコントロールしやすい
    wait_time = constant_throughput(1)
    # （任意）UI上のデフォルトのホストを指定する
    host = 'http://localhost:8080'

# 1リクエストが完了した段階で呼ばれるListenerの定義
@events.request.add_listener
def logging_response(request_type, name, response_time, response_length, response,
                     context, exception, **kwargs):
    # エラー発生時のみログ出力
    if not response.ok:
        requestId = context["requestId"]
        logging.info(f"[request event] path: {name} status: {response.status_code} body: {response.text} requestId: {requestId}")
