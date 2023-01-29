package service;

import DAO.DepartmentDAO;
import entity.Department;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService {

    private final DepartmentDAO departmentDAO;

    public DepartmentService(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public List<Department> getAll() {
        return departmentDAO.getAll();
    }

    public Department add(String name) throws SQLException {
        Department department = Department.builder().withName(name).build();
        int status = departmentDAO.create(department);
        if (status == 1) {
            department = departmentDAO.readLastEntry();
            return department;
        }
        return null;
    }

    public Department get(int id) {
        return departmentDAO.read(id);
    }

    public Department update(int id, String name) throws SQLException {
        Department updatedDepartment = Department.builder().withId(id).withName(name).build();
        if (departmentDAO.update(updatedDepartment) == 1) {
            return departmentDAO.read(id);
        }
        return null;
    }

    public boolean delete(int id) throws SQLException {
        return departmentDAO.delete(id);
    }
}
