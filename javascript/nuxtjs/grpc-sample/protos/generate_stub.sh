#!/bin/bash

proto_dir=$(dirname $0)

protoc -I ${proto_dir} greet.proto \
        --js_out=import_style=commonjs,binary:${proto_dir} \
        --grpc-web_out=import_style=commonjs,mode=grpcwebtext:${proto_dir}
