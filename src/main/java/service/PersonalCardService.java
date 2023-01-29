package service;

import DAO.DepartmentDAO;
import DAO.EmployeeDAO;
import DAO.PersonalCardDAO;
import entity.Department;
import entity.Employee;
import entity.PersonalCard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonalCardService {

    private final PersonalCardDAO personalCardDAO;

    private final DepartmentDAO departmentDAO;

    private final EmployeeDAO employeeDAO;

    public PersonalCardService(Connection connection) {
        this.personalCardDAO = new PersonalCardDAO(connection);
        this.departmentDAO = new DepartmentDAO(connection);
        this.employeeDAO = new EmployeeDAO(connection);
    }

    public List<PersonalCard> getAll() {
        return personalCardDAO.getAll();
    }

    public PersonalCard add(int departmentId, int employeeId) throws SQLException {
        PersonalCard personalCard = PersonalCard.builder()
                .withDepartment(departmentDAO.read(departmentId)).withEmployee(employeeDAO.read(employeeId))
                .build();
        int status = personalCardDAO.create(personalCard);
        if (status == 1){
            personalCard = personalCardDAO.readLastEntry();
            return personalCard;
        }
        return null;
    }

    public PersonalCard get(int id){
        return personalCardDAO.read(id);
    }

    public PersonalCard update(int id, int departmentId, int employeeId) throws SQLException {
        PersonalCard updPersonalCard = PersonalCard.builder()
                .withId(id)
                .withDepartment(departmentDAO.read(departmentId))
                .withEmployee(employeeDAO.read(employeeId))
                .build();
        if (personalCardDAO.update(updPersonalCard) == 1){
            return personalCardDAO.read(id);
        }
        return null;
    }

    public boolean delete(int id) throws SQLException {
            return personalCardDAO.delete(id);
    }
}
