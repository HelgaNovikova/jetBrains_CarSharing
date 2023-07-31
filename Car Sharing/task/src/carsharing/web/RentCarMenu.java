package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Car;
import carsharing.repository.model.UserContext;

import java.util.*;

public class RentCarMenu extends BaseMenu {

    private Map<Integer, MenuItem> rentCarMenu = new LinkedHashMap<>();

    public RentCarMenu(UserContext userContext, Scanner sc, CompanyRepository companyRepo,
                       CarRepository carRepo, CustomerRepository customerRepo) {
        createMenu(userContext, sc, companyRepo, carRepo, customerRepo);
        showMenu(rentCarMenu);
        runMenu(rentCarMenu, sc);
    }

    public void createMenu(UserContext userContext, Scanner sc, CompanyRepository companyRepo,
                           CarRepository carRepo, CustomerRepository customerRepo) {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Rent a car", 1) {
            @Override
            public void run(Scanner sc) {
                if (customerRepo.hasRentedCar(userContext.getCustomer())) {
                    System.out.println("You've already rented a car!");
                } else {
                    System.out.println("Choose the company:");
                    new CompaniesMenu(userContext, companyRepo, customerRepo, carRepo, sc);
                }
                showMenu(rentCarMenu);
            }
        });

        items.add(new MenuItem("Return a rented car", 2) {
            @Override
            public void run(Scanner sc) {
                if (!customerRepo.hasRentedCar(userContext.getCustomer())) {
                    System.out.println("You didn't rent a car!");
                } else {
                    customerRepo.returnRentedCar(userContext.getCustomer());
                    System.out.println("You've returned a rented car!");
                }
                showMenu(rentCarMenu);
            }
        });

        items.add(new MenuItem("My rented car", 3) {
            @Override
            public void run(Scanner sc) {
                if (!customerRepo.hasRentedCar(userContext.getCustomer())) {
                    System.out.println("You didn't rent a car!");
                } else {
                    Car rentedCar = customerRepo.getRentedCar(userContext.getCustomer());
                    System.out.println("Your rented car:");
                    System.out.println(rentedCar.getName());
                    System.out.println("Company:");
                    System.out.println(rentedCar.getCompany().getName());
                }
                showMenu(rentCarMenu);
            }
        });

        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {

            }
        });
        rentCarMenu = buildMap(items);
    }
}
