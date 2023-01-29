package service;

import DAO.EmployeeDAO;
import entity.Department;
import entity.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    public Employee add(String name, String surname, String address, String profession) throws SQLException {
        Employee employee = Employee.builder()
                .withName(name).withSurname(surname).withAddress(address).withProfession(profession)
                .build();
        int status = employeeDAO.create(employee);
        if (status == 1) {
            employee = employeeDAO.readLastEntry();
            return employee;
        }
        return null;
    }

    public Employee get(int id) {
        return employeeDAO.read(id);
    }

    public Employee update(int id, String name, String surname, String address, String profession) throws SQLException {
        Employee updatedEmployee = Employee.builder()
                .withId(id).withName(name).withSurname(surname).withAddress(address).withProfession(profession)
                .build();
        if (employeeDAO.update(updatedEmployee) == 1) {
            return employeeDAO.read(id);
        }
        return null;
    }

    public boolean delete(int id) throws SQLException {
        return employeeDAO.delete(id);
    }
}

