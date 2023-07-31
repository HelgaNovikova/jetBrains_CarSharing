package carsharing.web;

import carsharing.repository.impl.CarRepository;
import carsharing.repository.impl.CustomerRepository;
import carsharing.repository.model.Car;
import carsharing.repository.model.UserContext;

import java.util.*;

public class ChooseCarForRentMenu extends BaseMenu {

    private Map<Integer, MenuItem> carsForRentMenu = new LinkedHashMap<>();

    public ChooseCarForRentMenu(UserContext userContext, CarRepository carRepo, CustomerRepository customerRepo, Scanner sc) {
        createMenu(userContext, carRepo, customerRepo);
        showMenu(carsForRentMenu);
        runMenu(carsForRentMenu, sc);
    }

    @Override
    public void runMenu(Map<Integer, MenuItem> baseMenu, Scanner sc) {
        int choice = sc.nextInt();
        getMenuItemByChoice(baseMenu, choice).run(sc);
    }

   public void createMenu(UserContext userContext, CarRepository carRepo,CustomerRepository customerRepo) {
        List<Car> cars = carRepo.getAllCarsInCompany(userContext.getCompany());
        List<MenuItem> items = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            items.add(new MenuItem(cars.get(i).getName(), i + 1) {
                @Override
                public void run(Scanner sc) {
                    customerRepo.rentCar(userContext.getCustomer(), car);
                    System.out.println("You rented '" + car.getName() + "'");
                   // showMenu(carsForRentMenu);
                }
            });
        }
        items.add(new MenuItem("Back", 0) {
            @Override
            public void run(Scanner sc) {}
        });
        carsForRentMenu = buildMap(items);
    }
}
