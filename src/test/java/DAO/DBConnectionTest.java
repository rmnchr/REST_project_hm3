package DAO;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest extends AbstractDAOTest {

    @Test
    void getNewConnection() throws SQLException {
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
    }

    @Test
    void getObjectsFromDepartmentTable() throws SQLException {
        String[] expected = {"Legal", "Sales", "Accounting"};

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department")) {
            List<String> allDepartments = new ArrayList<>();
            while (resultSet.next()) {
                String department = resultSet.getString(2);
                allDepartments.add(department);
            }

            assertArrayEquals(expected, allDepartments.toArray());
        }
    }

    @Test
    void checkOnId() throws SQLException {
        Long[] expected = {1L, 2L, 3L};

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department")) {
            List<Long> allIds = new ArrayList<>();
            while (resultSet.next()) {
                long department = resultSet.getLong(1);
                allIds.add(department);
            }

            assertArrayEquals(expected, allIds.toArray());
        }
    }
}
