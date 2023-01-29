package DAO;

import entity.Department;
import entity.Employee;
import entity.PersonalCard;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalCardDAOTest extends AbstractDAOTest {

    @Test
    void createNewPersonalCard() throws SQLException {
        Department dp = departmentDAO.read(2);
        Employee emp = employeeDAO.read(3);

        PersonalCard personalCard = PersonalCard.builder().withId(0).withDepartment(dp).withEmployee(emp).build();
        Assert.assertEquals(1, personalCardDAO.create(personalCard));
    }

    @Test
    void getPersonalCard() {
        PersonalCard existedPersonalCard = personalCardDAO.read(2);
        PersonalCard nonExistedPersonalCard = personalCardDAO.read(10);

        assertEquals("Petr", existedPersonalCard.getEmployee().getName());
        assertNull(nonExistedPersonalCard);
    }

    @Test
    void updatingPersonalCard() throws SQLException {
        PersonalCard personalCard = PersonalCard.builder().withId(1)
                .withDepartment(departmentDAO.read(3)).withEmployee(employeeDAO.read(3)).build();
        Assert.assertEquals(1, personalCardDAO.update(personalCard));
    }

    @Test
    void deletePersonalCard() throws SQLException {
        personalCardDAO.delete(2);
        boolean notDeleted = personalCardDAO.delete(15);
        PersonalCard notFound = personalCardDAO.read(2);

        assertNull(notFound);
        assertFalse(notDeleted);
    }
}