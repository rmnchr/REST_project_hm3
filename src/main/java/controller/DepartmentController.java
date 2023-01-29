package controller;

import DAO.ConnectionPool;
import DAO.DepartmentDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import service.DepartmentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/all_departments")
public class DepartmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            DepartmentService departmentService = new DepartmentService((DepartmentDAO) connection);
            String allDepartments = mapper.writeValueAsString(departmentService.getAll());
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(allDepartments);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
