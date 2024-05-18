from sqlalchemy import Column, Integer, String, text
from sqlalchemy.dialects.postgresql import UUID
from .base import Base
import uuid
from sqlalchemy.orm import Mapped
from sqlalchemy.orm import mapped_column


class Employee(Base):
    __tablename__ = 'employees'

    id: Mapped[UUID] = mapped_column(UUID,
                                     primary_key=True,
                                     server_default=text('uuid_generate_v4()'),
                                     default=text('uuid_generate_v4()'))
    name: Mapped[str] = mapped_column(String(30))
    age: Mapped[int] = mapped_column(Integer)

    def __init__(self, name, age):
        self.name = name
        self.age = age
