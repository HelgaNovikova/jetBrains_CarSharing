package carsharing.repository.model;

public class UserContext {
    private boolean isManagerLogged;

    private Company company;

    private Customer customer;

    public boolean isManagerLogged() {
        return isManagerLogged;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    private Car car;

    public void setManagerLogged(boolean logged) {
        isManagerLogged = logged;
    }



}
