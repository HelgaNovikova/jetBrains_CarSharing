package carsharing.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {

    private final Connection conn;

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:./src/carsharing/db/";

    public Datasource(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + dbName);
        conn.setAutoCommit(true);
    }

    public Connection getConnection() {
        return conn;
    }
}
