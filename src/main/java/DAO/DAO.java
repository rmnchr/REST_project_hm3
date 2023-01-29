package DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    int create(T name) throws SQLException;
    T read(int id);
    int update(T name) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<T> getAll();
    void printSQLException(SQLException ex);
}
