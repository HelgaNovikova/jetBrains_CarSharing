package carsharing.repository.impl;

import carsharing.repository.Datasource;
import carsharing.repository.model.Car;
import carsharing.repository.model.Company;
import carsharing.repository.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private static final String GET_ALL_CUSTOMERS = "select * from CUSTOMER";

    private final Datasource ds;

    public CustomerRepository(Datasource ds) {
        this.ds = ds;
    }

    public void createCustomer(Customer entity) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "insert into CUSTOMER (NAME) values ('" + entity.getName() + "')";
            st.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> result = new ArrayList<>();
        try (Statement st = ds.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(GET_ALL_CUSTOMERS);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                result.add(new Customer(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Car rentCar(Customer customer, Car car) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "UPDATE CUSTOMER SET RENTED_CAR_ID=" + car.getId() +
                    " WHERE ID=" + customer.getId();
            st.executeUpdate(request);
            return car;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Car getRentedCar(Customer customer) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "SELECT CAR.NAME NAME, CAR.COMPANY_ID COMPANY_ID FROM CAR JOIN CUSTOMER " +
                    "ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                    " WHERE CUSTOMER.ID=" + customer.getId();
            ResultSet rs = st.executeQuery(request);
            String carName = "default";
            int companyId = 0;
            while (rs.next()) {
                carName = rs.getString("NAME");
                companyId = rs.getInt("COMPANY_ID");
            }
            String getCompany = "SELECT * FROM COMPANY WHERE ID=" + companyId;
            ResultSet company = st.executeQuery(getCompany);
            String companyName = "default";
            while (company.next()) {
                companyName = company.getString("NAME");
            }
            return new Car(carName, new Company(companyName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void returnRentedCar(Customer customer) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "UPDATE CUSTOMER SET RENTED_CAR_ID=NULL WHERE ID=" + customer.getId();
            st.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasRentedCar(Customer customer) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "select count (RENTED_CAR_ID) num from CUSTOMER where ID=" + customer.getId();
            ResultSet rs = st.executeQuery(request);
            int rentedCar = 0;
            while (rs.next()) {
                rentedCar = rs.getInt("num");
            }
            return (rentedCar != 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
