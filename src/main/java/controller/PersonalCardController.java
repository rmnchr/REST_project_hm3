package controller;

import DAO.ConnectionPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.PersonalCard;
import service.PersonalCardService;

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

@WebServlet(urlPatterns = {
        "/api/all_personalCard",
        "/api/delete_personalCard",
        "/api/get_personalCard",
        "/api/update_personalCard"})
public class PersonalCardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PersonalCardService personalCardService = new PersonalCardService(connection);
            String id = req.getParameter("id");
            String result;
            if (id == null) {
                result = mapper.writeValueAsString(personalCardService.getAll());
            } else {
                PersonalCard personalCard = personalCardService.get(Integer.parseInt(id));
                result = mapper.writeValueAsString(personalCard);
            }
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(result);
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String personalCardId = req.getParameter("id");
        int departmentId = Integer.parseInt(req.getParameter("department_id"));
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            PersonalCardService personalCardService = new PersonalCardService(connection);
            if (personalCardId == null) {
                personalCardService.add(departmentId, employeeId);
            } else {
                personalCardService.update(Integer.parseInt(personalCardId), departmentId, employeeId);
            }
            resp.setStatus(200);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}