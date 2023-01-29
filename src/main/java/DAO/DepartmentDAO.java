package DAO;

import entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO implements DAO<Department> {

    private static final String INSERT_DEPARTMENT_SQL = "INSERT INTO department (department_name) VALUES (?);";
    private static final String SELECT_DEPARTMENT_BY_ID = "SELECT * FROM department WHERE department_id = (?);";
    private static final String SELECT_ALL_DEPARTMENT = "SELECT * FROM department";
    private static final String DELETE_DEPARTMENT_SQL = "DELETE FROM department WHERE department_id = (?);";
    private static final String UPDATE_DEPARTMENT_SQL = "UPDATE department SET department_name = (?) WHERE department_id = (?);";
    private static final String GET_LAST_ENTRY_DEPARTMENT = "SELECT TOP 1 * FROM department ORDER BY department_id DESC";


    private final Connection connection;

    public DepartmentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(Department name) throws SQLException {
        int perem = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEPARTMENT_SQL)) {
            preparedStatement.setString(1, name.getName());
            perem = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return perem;
    }

    @Override
    public Department read(int id) {
        Department department = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DEPARTMENT_BY_ID);) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                department = Mapper.mapDepartment(rs);
                rs.close();
                return department;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public int update(Department name) throws SQLException {
        int perem = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DEPARTMENT_SQL);) {

            statement.setString(1, name.getName());
            statement.setInt(2, name.getId());

            perem = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perem;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(DELETE_DEPARTMENT_SQL);) {
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
    public List<Department> getAll() {
        List<Department> allDepartments = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(SELECT_ALL_DEPARTMENT);
            while (resultSet.next()) {
                Department department = Mapper.mapDepartment(resultSet);
                allDepartments.add(department);
            }
            resultSet.close();
            return allDepartments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Department readLastEntry(){
        Department dpt = null;
        try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY_DEPARTMENT);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                dpt = Mapper.mapDepartment(resultSet);
            }
            return dpt;
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