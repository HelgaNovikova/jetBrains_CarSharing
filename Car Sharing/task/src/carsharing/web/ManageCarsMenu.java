package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.model.Car;
import carsharing.repository.model.Company;

import java.util.*;

public class ManageCarsMenu extends BaseMenu{
    private Map<Integer, MenuItem> carsMenu = new LinkedHashMap<>();

    public ManageCarsMenu(CarRepository carRepo, Company company, Scanner sc) {
        createMenu(carRepo, company);
        showMenu(carsMenu);
        runMenu(carsMenu, sc);
    }

    public void createMenu(CarRepository carRepo, Company company) {
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("Car list", 1) {
            @Override
            public void run(Scanner sc) {
                List<Car> cars = carRepo.getAllCarsInCompany(company);
                if (cars.isEmpty()) {
                    System.out.println("The car list is empty!");
                } else {
                    System.out.println("Car list:");
                    for (int i = 0; i < cars.size(); i++) {
                        System.out.println(i + 1 + ". " + cars.get(i).getName());
                    }
                }
                showMenu(carsMenu);
            }
        });

        items.add(new MenuItem("Create a car", 2) {
            @Override
            public void run(Scanner sc) {
                System.out.println("Enter the car name:");
                sc.nextLine();
                String name = sc.nextLine();
                carRepo.createCar(new Car(name,company));
                System.out.println("The car was added!");
                System.out.println();
                showMenu(carsMenu);
            }
        });

        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {
            }
        });
        carsMenu = buildMap(items);
    }
}
