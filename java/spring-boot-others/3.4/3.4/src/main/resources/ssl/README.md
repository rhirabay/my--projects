
```shell
openssl req -x509 -new -newkey rsa:2048 -nodes -keyout ca.key -out ca.pem \
  -config ca-openssl.cnf -days 3650 -extensions v3_req
openssl genrsa -out client.key.rsa 2048
openssl pkcs8 -topk8 -in client.key.rsa -out client.key -nocrypt
openssl req -new -key client.key -out client.csr

openssl x509 -req -CA ca.pem -CAkey ca.key -CAcreateserial -in client.csr \
  -out client.pem -days 3650

openssl genrsa -out server.key.rsa 2048
openssl pkcs8 -topk8 -in server.key.rsa -out server0.key -nocrypt
openssl req -new -key server.key -out server.csr

openssl x509 -req -CA ca.pem -CAkey ca.key -CAcreateserial -in server.csr \
  -out server.pem -days 3650
```

```shell
openssl pkcs12 -export -in server.pem -inkey server.key -out keystore.p12 -name sample
```