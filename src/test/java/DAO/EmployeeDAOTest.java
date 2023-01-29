package DAO;

import entity.Department;
import entity.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest extends AbstractDAOTest {

    @Test
    void createNewEmployee() throws SQLException {
        Employee employee = Employee.builder().withName("Anna").withSurname("Manevskaya")
                .withAddress("Minsk, Big Street 68").withProfession("Sales").build();
        Assert.assertEquals(1, employeeDAO.create(employee));
        Assert.assertEquals(employee.getName(), employeeDAO.readLastEntry().getName());
    }

    @Test
    void getEmployee() {
        Employee noSuchId = employeeDAO.read(10);
        Employee negativeId = employeeDAO.read(-5);

        assertNull(noSuchId);
        assertNull(negativeId);
    }

    @Test
    void updatingEmployee() throws SQLException {
        Employee employee = Employee.builder().withId(1).withName("Anton")
                .withAddress("Minsk, Down Street 20").withProfession("Driver").build();
        Assert.assertEquals(1, employeeDAO.update(employee));
        Assert.assertEquals(employee.getName(), employeeDAO.read(1).getName());
    }

    @Test
    void deleteEmployee() throws SQLException {
        int existedEmployee = 2;
        int nonExistedEmployee = 9;

        boolean deleteTrue = employeeDAO.delete(existedEmployee);
        boolean deleteFalse = employeeDAO.delete(nonExistedEmployee);

        assertFalse(deleteFalse);
        assertTrue(deleteTrue);
    }
}