import sqlalchemy as sa
from sqlalchemy.orm import Session
from sqlalchemy.ext.declarative import declarative_base

from src.postgres.model.base import Base
from src.postgres.model.employee import Employee


class EmployeeRepository:
    def __init__(self):
        self.engine = sa.create_engine(
            sa.engine.url.URL.create(
                drivername="postgresql",
                username="postgres",
                password='password',
                host='localhost',
                port=5432,
                database='sampledb',
            )
        )
        self.session = self.create_session()

    def create_table(self):
        Employee.metadata.create_all(bind=self.engine)

    def create_session(self):
        from sqlalchemy.orm import scoped_session, sessionmaker

        # Sessionの作成
        return scoped_session(
            sessionmaker(
                autocommit=False,
                autoflush=False,
                bind=self.engine
            )
        )

    def add(self, employee: Employee) -> Employee:
        self.session.add(employee)
        self.session.commit()
        self.session.flush()
        return employee

    def find_all(self) -> list[Employee]:
        return self.session.query(Employee).all()
