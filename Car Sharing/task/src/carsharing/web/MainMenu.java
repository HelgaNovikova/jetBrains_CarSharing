package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Customer;
import carsharing.repository.model.UserContext;

import java.util.*;

public class MainMenu extends BaseMenu {

    private Map<Integer, MenuItem> mainMenu = new LinkedHashMap<>();

    public MainMenu(Scanner sc, UserContext userContext, CompanyRepository companyRepo,
                    CarRepository carRepo, CustomerRepository customerRepo) {
        createMenu(userContext, companyRepo, carRepo, customerRepo);
        showMenu(mainMenu);
        runMenu(mainMenu, sc);
    }

    public void createMenu(UserContext userContext, CompanyRepository companyRepo,
                           CarRepository carRepo, CustomerRepository customerRepo) {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Log in as a manager", 1) {
            @Override
            public void run(Scanner sc) {
                userContext.setManagerLogged(true);
                new ManagerMenu(sc, userContext, companyRepo, customerRepo, carRepo);
                showMenu(mainMenu);
            }
        });

        items.add(new MenuItem("Log in as a customer", 2) {
            @Override
            public void run(Scanner sc) {
                List<Customer> customers = customerRepo.getAllCustomers();
                if (customers.isEmpty()) {
                    System.out.println("The customer list is empty!");
                } else {
                    System.out.println("Choose a customer:");
                    new ChooseCustomerMenu(userContext, customerRepo, companyRepo, carRepo, sc);
                }
                showMenu(mainMenu);
            }
        });

        items.add(new MenuItem("Create a customer", 3) {
            @Override
            public void run(Scanner sc) {
                System.out.println("Enter the customer name:");
                sc.nextLine();
                String name = sc.nextLine();
                customerRepo.createCustomer(new Customer(name));
                showMenu(mainMenu);
            }
        });

        items.add(new MenuItem("Exit", 0) {
            @Override
            public void run(Scanner sc) {
                System.exit(0);
            }
        });

        mainMenu = buildMap(items);
    }
}
