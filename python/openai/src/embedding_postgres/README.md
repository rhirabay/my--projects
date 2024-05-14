
```shell
docker volume create openai_postgres_embedding_data
```

```shell
docker compose stop \
  && docker compose up -d \
  && docker compose ps
```
