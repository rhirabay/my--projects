```shell
docker exec -it postgres /bin/bash

psql -U postgres -d sampledb

# uuid_generate_v4() を利用するための拡張機能を有効化
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```