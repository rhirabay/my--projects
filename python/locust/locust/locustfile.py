from locust import task, constant, HttpUser

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

    # スレッドごとの実行間隔の指定
    # 1スレッド1rps以上の性能がでないように1に設定しておくとコントロールしやすい
    wait_time = constant(1)
    # （任意）UI上のデフォルトのホストを指定する
    host = 'http://localhost:8080'
