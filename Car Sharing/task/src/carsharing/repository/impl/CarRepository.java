package carsharing.repository.impl;

import carsharing.repository.Datasource;
import carsharing.repository.model.Car;
import carsharing.repository.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private final Datasource ds;

    private static final String GET_ALL_CARS = "select * from CAR where company_id=";

    public CarRepository(Datasource ds) {
        this.ds = ds;
    }

    public void createCar(Car entity) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "insert into CAR (NAME, COMPANY_ID) values ('" + entity.getName() + "', " +
                    entity.getCompany().getId() + ")";
            st.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> getAllCarsInCompany(Company company) {
        List<Car> result = new ArrayList<>();
        try (Statement st = ds.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(GET_ALL_CARS + company.getId());
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                result.add(new Car(id, name, company));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
