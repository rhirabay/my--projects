CREATE TABLE IF NOT EXISTS PAYMENT_TRANSACTION (
    id         VARCHAR(10) NOT NULL,
    status     CHAR(1) NOT NULL,
    created_at TIMESTAMP, -- 作成日
    updated_at TIMESTAMP, -- 更新日
    PRIMARY KEY(id)
);