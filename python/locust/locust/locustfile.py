from locust import task, constant_throughput, HttpUser

# クラス名は任意（HttpUser）を継承する
class SampleUser(HttpUser):
    # API(シナリオ)ごとに@taskを付与したメソッドを定義する
    # @taskの引数には負荷をかける比重（この実装だとget:post = 2:1の割合でAPIコールする）
    @task(2)
    def get_request(self):
        value = "value"
        self.client.get(f"/sample?key={value}")

    @task(1)
    def post_request(self):
        self.client.post("/sample", json={"key": "value"})

    # 1秒間の最大実行数を指定
    # 1に指定しておけばスレッド数以上の性能がでないので、負荷をコントロールしやすい
    wait_time = constant_throughput(1)
    # （任意）UI上のデフォルトのホストを指定する
    host = 'http://localhost:8080'
