package DAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDAOTest {
    static Connection connection;

    static DepartmentDAO departmentDAO;
    static EmployeeDAO employeeDAO;
    static PersonalCardDAO personalCardDAO;

    @BeforeAll
    static void initialize() throws SQLException {
        connection = DriverManager.getConnection(
                Driver.H2_TEST.getUrl(), "admin", "admin");
        connection.setAutoCommit(false);
        departmentDAO = new DepartmentDAO(connection);
        employeeDAO = new EmployeeDAO(connection);
        personalCardDAO = new PersonalCardDAO(connection);
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
    }
}
