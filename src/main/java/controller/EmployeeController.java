package controller;

import DAO.ConnectionPool;
import DAO.EmployeeDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/save_employee")
public class EmployeeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeID = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String address = req.getParameter("address");
        String profession = req.getParameter("profession");
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            EmployeeService employeeService = new EmployeeService((EmployeeDAO) connection);
            if (employeeID == null) {
                employeeService.add(name, surname, address, profession);
            } else {
                employeeService.update(Integer.parseInt(employeeID), name, surname, address, profession);
            }
            resp.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}