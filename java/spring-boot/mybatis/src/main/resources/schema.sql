CREATE TABLE IF NOT EXISTS TODO_LIST (
    id    VARCHAR(10) NOT NULL,
    title VARCHAR(10),
    created_by VARCHAR(10), -- 作成者
    created_at TIMESTAMP, -- 作成日時
    PRIMARY KEY(id)
);