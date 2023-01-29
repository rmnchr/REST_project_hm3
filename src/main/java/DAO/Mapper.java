package DAO;

import entity.Department;
import entity.Employee;
import entity.PersonalCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mapper {

    static final String departmentIdField = "department_id";
    static final String employeeIdField = "employee_id";
    static final String personalCardIdField = "personalCard_id";

    static final String departmentNameField = "department_name";
    static final String employeeNameField = "employee_name";
    static final String employeeSurnameField = "employee_surname";
    static final String employeeAddressIdField = "employee_address";
    static final String employeeProfessionIdField = "employee_profession";


    static Department mapDepartment(ResultSet resultSet) throws SQLException {
        return Department.builder()
                .withId(resultSet.getInt(departmentIdField))
                .withName(resultSet.getString(departmentNameField))
                .build();
    }

    static Employee mapEmployee(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .withId(resultSet.getInt(employeeIdField))
                .withName(resultSet.getString(employeeNameField))
                .withSurname(resultSet.getString(employeeSurnameField))
                .withAddress(resultSet.getString(employeeAddressIdField))
                .withProfession(resultSet.getString(employeeProfessionIdField))
                .build();
    }

    static PersonalCard mapPersonalCard(ResultSet resultSet) throws SQLException {
        return PersonalCard.builder()
                .withId(resultSet.getInt(personalCardIdField))
                .withDepartment(mapDepartment(resultSet))
                .withEmployee(mapEmployee(resultSet))
                .build();
    }
}
