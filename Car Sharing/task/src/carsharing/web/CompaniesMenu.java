package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CompanyRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Company;
import carsharing.repository.model.UserContext;

import java.util.*;

public class CompaniesMenu extends BaseMenu {
    private Map<Integer, MenuItem> companiesMenu = new LinkedHashMap<>();

    public CompaniesMenu(UserContext userContext, CompanyRepository companyRepo,
                         CustomerRepository customerRepo, CarRepository carRepo, Scanner sc) {
        createMenu(userContext, companyRepo, customerRepo, carRepo);
        showMenu(companiesMenu);
        runMenu(companiesMenu, sc);
    }

    @Override
    public void runMenu(Map<Integer, MenuItem> baseMenu, Scanner sc) {
        int choice = sc.nextInt();
        getMenuItemByChoice(baseMenu, choice).run(sc);
    }

    public void createMenu(UserContext userContext, CompanyRepository companyRepo,
                           CustomerRepository customerRepo, CarRepository carRepo) {
        List<Company> companies = companyRepo.getAllCompanies();
        List<MenuItem> items = new ArrayList<>();
        for (Company company : companies) {
            items.add(new MenuItem(company.getName(), company.getId()) {
                @Override
                public void run(Scanner sc) {
                    if (userContext.getCustomer() != null) {
                        userContext.setCompany(company);
                        if (!carRepo.getAllCarsInCompany(company).isEmpty()) {
                            System.out.println("Choose a car:");
                            new ChooseCarForRentMenu(userContext, carRepo, customerRepo, sc);
                        } else {
                            System.out.println("No available cars in the '" +
                                    company.getName() + "' company");
                            showMenu(companiesMenu);
                        }
//                        showMenu(companiesMenu);
                    } else {
                        System.out.println("'" + company.getName() + "' company");
                        new ManageCarsMenu(carRepo, company, sc);
                    }
                }
            });
        }
        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {
            }
        });

        companiesMenu = buildMap(items);
    }

}
