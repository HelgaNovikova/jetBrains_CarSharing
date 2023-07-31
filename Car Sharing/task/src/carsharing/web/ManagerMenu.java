package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Company;
import carsharing.repository.model.UserContext;

import java.util.*;

public class ManagerMenu extends BaseMenu {

    private Map<Integer, MenuItem> managerMenuItems = new LinkedHashMap<>();

    public ManagerMenu(Scanner sc, UserContext userContext, CompanyRepository companyRepo,
                       CustomerRepository customerRepo, CarRepository carRepo) {
        createMenu(sc, userContext, companyRepo, customerRepo, carRepo);
        showMenu(managerMenuItems);
        runMenu(managerMenuItems, sc);
    }

    public void createMenu(Scanner sc, UserContext userContext, CompanyRepository companyRepo,
                           CustomerRepository customerRepo, CarRepository carRepo) {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Company list", 1) {
            @Override
            public void run(Scanner sc) {
                List<Company> companies = companyRepo.getAllCompanies();
                if (companies.isEmpty()) {
                    System.out.println("The company list is empty!");
                    showMenu(managerMenuItems);
                } else {
                    System.out.println("Choose the company:");
                    new CompaniesMenu(userContext, companyRepo, customerRepo, carRepo, sc);
                    showMenu(managerMenuItems);
                }
            }
        });

        items.add(new MenuItem("Create a company", 2) {
            @Override
            public void run(Scanner sc) {
                System.out.println("Enter the company name:");
                sc.nextLine();
                String name = sc.nextLine();
                companyRepo.createCompany(new Company(name));
                System.out.println("The company was created!");
                System.out.println();
                showMenu(managerMenuItems);
            }
        });

        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {
                userContext.setManagerLogged(false);
            }
        });
        managerMenuItems = buildMap(items);
    }
}
