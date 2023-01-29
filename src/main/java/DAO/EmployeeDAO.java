package DAO;

import entity.Department;
import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {

    private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employee " +
            "(employee_name, employee_surname, employee_address, employee_profession) VALUES (?, ?, ?, ?);";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE employee_id = (?)";
    private static final String SELECT_ALL_EMPLOYEE = "SELECT * FROM employee";
    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employee WHERE employee_id = ?;";
    private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employee SET " +
            "employee_name = (?), employee_surname = (?), employee_address = (?), employee_profession = (?) WHERE employee_id = (?);";
    private static final String GET_LAST_ENTRY_EMPLOYEE = "SELECT TOP 1 * FROM employee ORDER BY employee_id DESC";

    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public int create(Employee name) throws SQLException {
        int perem = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL)) {
            preparedStatement.setString(1, name.getName());
            preparedStatement.setString(2, name.getSurname());
            preparedStatement.setString(3, name.getAddress());
            preparedStatement.setString(4, name.getProfession());
            perem = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return perem;
    }


    @Override
    public Employee read(int id) {
        Employee employee = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                employee = Mapper.mapEmployee(rs);
                rs.close();
                return employee;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }


    @Override
    public int update(Employee name) throws SQLException {
        int perem = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL);) {

            statement.setString(1, name.getName());
            statement.setString(2, name.getSurname());
            statement.setString(3, name.getAddress());
            statement.setString(4, name.getProfession());
            statement.setInt(5, name.getId());

            perem = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perem;
    }


    @Override
    public boolean delete(int id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> allEmployee = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(SELECT_ALL_EMPLOYEE);
            while (resultSet.next()) {
                Employee employee = Mapper.mapEmployee(resultSet);
                allEmployee.add(employee);
            }
            resultSet.close();
            return allEmployee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee readLastEntry(){
        Employee emp = null;
        try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY_EMPLOYEE);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                emp = Mapper.mapEmployee(resultSet);
            }
            return emp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);

                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());

                Throwable t = ex.getCause();

                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}