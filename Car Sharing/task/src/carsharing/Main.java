package carsharing;

import carsharing.repository.DatabaseInitializer;
import carsharing.repository.Datasource;
import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.UserContext;
import carsharing.web.MainMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String dbName = "carsharing";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-databaseFileName")) {
                dbName = args[i + 1];
            }
        }

        Datasource ds = new Datasource(dbName);
        DatabaseInitializer di = new DatabaseInitializer(ds);
        UserContext userContext = new UserContext();
        CompanyRepository companyRepo = new CompanyRepository(ds);
        CarRepository carRepo = new CarRepository(ds);
        CustomerRepository customerRepo = new CustomerRepository(ds);
        Scanner sc = new Scanner(System.in);
        new MainMenu(sc, userContext, companyRepo, carRepo, customerRepo);


    }
}