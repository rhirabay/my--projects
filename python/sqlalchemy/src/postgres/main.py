import sys
import os
print(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

from src.postgres.repository.employee_repository import EmployeeRepository
from src.postgres.model.employee import Employee


employee_repository = EmployeeRepository()

#employee_repository.create_table()

# DBにレコードの追加
employee = Employee(name='ryo', age=31)
employee = employee_repository.add(employee)
print('*** employee')
print(f'id: {employee.id}, name: {employee.name}, age: {employee.age}')
employee_list = employee_repository.find_all()
print('*** employee by find_all')
for employee in employee_list:
    print(f'id: {employee.id}, name: {employee.name}, age: {employee.age}')
