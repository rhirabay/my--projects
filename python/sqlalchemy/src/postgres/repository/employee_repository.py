from src.postgres.model.employee import Employee
from src.postgres.repository.datasource import create_session


class EmployeeRepository:
    def __init__(self):
        self.session = create_session()


    def add(self, employee: Employee) -> Employee:
        self.session.add(employee)
        self.session.commit()
        self.session.flush()
        return employee

    def find_all(self) -> list[Employee]:
        return self.session.query(Employee).all()
