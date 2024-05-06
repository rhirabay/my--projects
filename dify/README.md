# 準備


```shell
# ディレクトリ作成
mkdir -p docker/nginx/conf.d
```

```shell
required_files=(
    "docker/nginx/conf.d/default.conf"
    "docker/nginx/nginx.conf"
    "docker/nginx/proxy.conf"
    "docker/docker-compose.yaml"
)
for required_file in ${required_files[@]}; do
    curl -s -o ${required_file} \
      https://raw.githubusercontent.com/langgenius/dify/main/${required_file}
done
```

```shell
volume_dirs=(
    docker/volumes/redis/data
    docker/volumes/app/storage
    docker/volumes/db/data/pgdata
    docker/volumes/weaviate
)
for volume_dir in ${volume_dirs[@]}; do
    mkdir -p ${volume_dir}
    chmod 777 ${volume_dir}
done
```



