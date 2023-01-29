package DAO;

import entity.Employee;
import entity.PersonalCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalCardDAO implements DAO<PersonalCard> {

    private static final String INSERT_PERSONALCARD_SQL = "INSERT INTO personalCard"
            + " (department_id, employee_id) VALUES (?, ?);";
    private static final String SELECT_PERSONALCARD_BY_ID =
            "SELECT personalCard_id, department.department_id, department_name, " +
                                    "employee.employee_id, employee_name, employee_surname, employee_address, employee_profession " +
            "FROM personalCard JOIN employee ON personalCard.employee_id = employee.employee_id " +
                              "JOIN department ON personalCard.department_id = department.department_id " +
            "WHERE personalCard_id = (?);";
    private static final String SELECT_ALL_PERSONALCARD =
            "SELECT personalCard_id, department.department_id, department_name, " +
                    "employee.employee_id, employee_name, employee_surname, employee_address, employee_profession " +
            "FROM personalCard JOIN employee ON personalCard.employee_id = employee.employee_id " +
                              "JOIN department ON personalCard.department_id = department.department_id";
    private static final String DELETE_PERSONALCARD_SQL = "DELETE FROM personalCard WHERE personalCard_id = (?);";
    private static final String UPDATE_PERSONALCARD_SQL = "UPDATE personalCard SET department_id = (?), employee_id = (?) " +
                                                          "WHERE employee_id = (?);";

    private static final String GET_LAST_ENTRY_PERSONALCARD=
            "SELECT TOP 1 * FROM personalCard ORDER BY personalCard_id DESC";

    private final Connection connection;

    public PersonalCardDAO(Connection connection) {
        this.connection = connection;

    }

    @Override
    public int create(PersonalCard name) throws SQLException {
        int perem = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSONALCARD_SQL)) {
            preparedStatement.setInt(1, name.getDepartment().getId());
            preparedStatement.setInt(2, name.getEmployee().getId());
            perem = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return perem;
    }

    @Override
    public PersonalCard read(int id) {
        PersonalCard personalCard = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PERSONALCARD_BY_ID);) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                personalCard = Mapper.mapPersonalCard(rs);
                rs.close();
                return personalCard;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    @Override
    public int update(PersonalCard name) throws SQLException {
        int perem = 0;

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERSONALCARD_SQL);) {

            statement.setInt(1, name.getDepartment().getId());
            statement.setInt(2, name.getEmployee().getId());
            statement.setInt(3, name.getEmployee().getId());

            perem = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return perem;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean rowDeleted;

        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERSONALCARD_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowDeleted;
    }

    @Override
    public List<PersonalCard> getAll() {
        List<PersonalCard> allPersonalCard = new ArrayList<>();

        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(SELECT_ALL_PERSONALCARD);

            while (resultSet.next()) {
                PersonalCard personalCard = Mapper.mapPersonalCard(resultSet);
                allPersonalCard.add(personalCard);
            }
            resultSet.close();
            return allPersonalCard;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonalCard readLastEntry(){
        PersonalCard pc = null;
        try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY_PERSONALCARD);
            ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()){
                pc = Mapper.mapPersonalCard(resultSet);
            }
            return pc;
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