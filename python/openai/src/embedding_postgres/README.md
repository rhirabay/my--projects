
```shell
docker volume create openai_postgres_embedding_data
```

```shell
docker compose stop \
  && docker compose up -d \
  && docker compose ps
```

#### Postgresログイン

```shell
docker exec -it mypj-postgres /bin/bash
psql -U postgres -d mydb
```

#### ベクトルストア拡張有効化

```shell
CREATE EXTENSION vector;
```
