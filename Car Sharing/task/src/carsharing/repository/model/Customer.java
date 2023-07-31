package carsharing.repository.model;

public class Customer {
    private int id;
    private String name;

    private Car rentedCar;

    public int getId() {
        return id;
    }


    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

}
