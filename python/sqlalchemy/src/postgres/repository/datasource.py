import sqlalchemy as sa

ENGINE = sa.create_engine(
    sa.engine.url.URL.create(
        drivername="postgresql",
        username="postgres",
        password='password',
        host='localhost',
        port=5432,
        database='sampledb',
    )
)

def create_session():
    from sqlalchemy.orm import scoped_session, sessionmaker

    # Sessionの作成
    return scoped_session(
        sessionmaker(
            autocommit=False,
            autoflush=False,
            bind=ENGINE
        )
    )

