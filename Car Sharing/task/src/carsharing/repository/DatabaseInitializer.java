package carsharing.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public DatabaseInitializer(Datasource ds) throws SQLException {
        Statement stmt;
        Connection conn = ds.getConnection();
        stmt = conn.createStatement();
        String createTableCompany = "CREATE TABLE if not exists  COMPANY " +
                "(ID INT not NULL auto_increment, " +
                "NAME VARCHAR(255) not NULL, " +
                "PRIMARY KEY (ID)," +
                "UNIQUE KEY name (NAME))";
        String dropTableCompany = "DROP TABLE IF EXISTS COMPANY";
        String dropTableCar = "DROP TABLE IF EXISTS CAR";
        String dropTableCustomer = "DROP TABLE IF EXISTS CUSTOMER";
//        stmt.executeUpdate(dropTableCustomer);
//        stmt.executeUpdate(dropTableCar);
//        stmt.executeUpdate(dropTableCompany);
        stmt.executeUpdate(createTableCompany);
        String createTableCar = "CREATE TABLE if not exists CAR " +
                "(ID INT NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR(255) NOT NULL, " +
                "COMPANY_ID INT NOT NULL, " +
                "UNIQUE KEY (NAME), " +
                "PRIMARY KEY (ID), " +
                "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
        stmt.executeUpdate(createTableCar);
        String createTableCustomer = "CREATE TABLE if not exists  CUSTOMER " +
                "(ID INT NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR(255) NOT NULL, " +
                "RENTED_CAR_ID INT, " +
                "PRIMARY KEY (ID), " +
                "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
        stmt.executeUpdate(createTableCustomer);
        stmt.close();
    }

}
