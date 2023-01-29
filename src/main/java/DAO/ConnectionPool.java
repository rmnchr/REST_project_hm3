package DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.beans.PropertyVetoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionPool {
    private static ComboPooledDataSource comboPooledDataSource;

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        if (comboPooledDataSource == null) {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("org.postgresql.Driver");
            comboPooledDataSource.setJdbcUrl(System.getenv("POSTGRES_URL"));
            comboPooledDataSource.setUser(System.getenv("POSTGRES_USER"));
            comboPooledDataSource.setPassword(System.getenv("POSTGRES_PW"));

            comboPooledDataSource.setInitialPoolSize(5);
            comboPooledDataSource.setMinPoolSize(5);
            comboPooledDataSource.setAcquireIncrement(5);
            comboPooledDataSource.setMaxPoolSize(20);
            comboPooledDataSource.setMaxStatements(100);
            return comboPooledDataSource;
        }
        return comboPooledDataSource;
    }
}
