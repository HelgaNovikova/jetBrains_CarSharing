package carsharing.repository.impl;

import carsharing.repository.Datasource;
import carsharing.repository.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {

    private static final String GET_ALL_COMPANIES = "select * from COMPANY";

    private final Datasource ds;

    public CompanyRepository(Datasource ds) {
        this.ds = ds;
    }

    public void createCompany(Company entity) {
        try (Statement st = ds.getConnection().createStatement()) {
            String request = "insert into COMPANY (NAME) values ('" + entity.getName() + "')";
            st.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Company> getAllCompanies() {
        List<Company> result = new ArrayList<>();
        try (Statement st = ds.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(GET_ALL_COMPANIES);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                result.add(new Company(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}