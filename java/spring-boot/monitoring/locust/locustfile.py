from locust import task, constant_throughput, HttpUser

class SampleUser(HttpUser):
    @task(2)
    def get_request(self):
        value = "value"
        self.client.get(f"/client?key={value}")

    wait_time = constant_throughput(1)
    # （任意）UI上のデフォルトのホストを指定する
    host = 'http://localhost:8080'