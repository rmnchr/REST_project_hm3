package DAO;

import entity.Department;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDAOTest extends AbstractDAOTest {

    @Test
    void createNewDepartment() throws SQLException {
        Department department = Department.builder().withId(1).withName("Finance").build();
        Assert.assertEquals(1, departmentDAO.create(department));
        Assert.assertEquals(department.getName(), departmentDAO.readLastEntry().getName());
    }

    @Test
    void getDepartment() {
        Department noSuchId = departmentDAO.read(10);
        Department negativeId = departmentDAO.read(-5);

        assertNull(noSuchId);
        assertNull(negativeId);
    }

    @Test
    void updatingDepartment() throws SQLException {
        Department department = Department.builder().withId(1).withName("GSg").build();
        Assert.assertEquals(1, departmentDAO.update(department));
        Assert.assertEquals(department.getName(), departmentDAO.read(1).getName());
    }

    @Test
    void deleteDepartment() throws SQLException {
        int existedPlayground = 2;
        int nonExistedPlayground = 9;

        boolean deleteTrue = departmentDAO.delete(existedPlayground);
        boolean deleteFalse = departmentDAO.delete(nonExistedPlayground);

        assertFalse(deleteFalse);
        assertTrue(deleteTrue);
    }
}