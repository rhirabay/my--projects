CREATE KEYSPACE sample
    WITH REPLICATION = {'class':'SimpleStrategy','replication_factor':1};

CREATE TABLE sample.t_sample (key text PRIMARY KEY, value text);

INSERT INTO sample.t_sample (key, value) VALUES ('key1', 'value1');