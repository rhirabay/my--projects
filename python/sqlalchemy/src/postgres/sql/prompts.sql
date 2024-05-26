CREATE TABLE employees (
    id uuid primary key DEFAULT uuid_generate_v4(),
    name varchar(20),
    age int
);