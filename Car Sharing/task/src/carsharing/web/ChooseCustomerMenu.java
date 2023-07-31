package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Customer;
import carsharing.repository.model.UserContext;

import java.util.*;

public class ChooseCustomerMenu extends BaseMenu {

    private Map<Integer, MenuItem> chooseCustomerMenu = new LinkedHashMap<>();

    public ChooseCustomerMenu(UserContext userContext,
                              CustomerRepository customerRepo,
                              CompanyRepository companyRepo,
                              CarRepository carRepo,
                              Scanner sc) {
        createMenu(userContext, customerRepo, companyRepo, carRepo);
        showMenu(chooseCustomerMenu);
        runMenu(chooseCustomerMenu, sc);
    }

    public void createMenu(UserContext userContext, CustomerRepository customerRepo,
                           CompanyRepository companyRepo, CarRepository carRepo) {
        List<Customer> customers = customerRepo.getAllCustomers();
        List<MenuItem> items = new ArrayList<>();
        for (Customer customer : customers) {
            items.add(new MenuItem(customer.getName(), customer.getId()) {
                @Override
                public void run(Scanner sc) {
                    userContext.setCustomer(customer);
                    userContext.setCar(null);
                    new RentCarMenu(userContext, sc, companyRepo, carRepo, customerRepo);
                    showMenu(chooseCustomerMenu);
                }
            });
        }
        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {
            }
        });

        chooseCustomerMenu = buildMap(items);
    }
}
