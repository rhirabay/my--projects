from locust import task, constant_throughput, HttpUser

class SampleUser(HttpUser):
    @task
    def get_request(self):
        self.client.get('/server?sleep=1')

    wait_time = constant_throughput(1)
    # （任意）UI上のデフォルトのホストを指定する
    host = 'http://localhost:8080'