CREATE TABLE EMBEDDING_DOCUMENT(
    -- 主キー
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    -- ドキュメント
    document TEXT NOT NULL,
    -- ベクトル
    embedding vector(1536) NOT NULL
);
