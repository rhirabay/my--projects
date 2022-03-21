import grpc from 'k6/net/grpc';
import { check, sleep } from 'k6';

const client = new grpc.Client();
// first arg : an array of paths to search for proto files.
// second arg: the name of the file to load.
client.load(['../../proto/src/main/proto/'], 'sample.proto');

export const options = {
  duration: "3s",
  // stages: [
  //   { duration: "5s", target: 1 },
  //   { duration: "5s", target: 10 },
  //   { duration: "10s", target: 30 },
  //   { duration: '10s', target: 0 },
  // ],
}

export default () => {
  client.connect('127.0.0.1:6565', {
    plaintext: true
  });

  const data = { name: 'Ryo' };
  const response = client.invoke('greet.Greet/greeting', data);

  check(response, {
    'status is OK': (r) => r && r.status === grpc.StatusOK,
  });

  console.log(JSON.stringify(response.message));
  sleep(1);
  client.close()
};

