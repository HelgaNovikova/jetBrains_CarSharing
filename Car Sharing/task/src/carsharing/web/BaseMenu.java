package carsharing.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public abstract class BaseMenu {

    public LinkedHashMap<Integer, MenuItem> buildMap(List<MenuItem> items) {
        return items.stream()
                .collect(Collectors.toMap(i -> i.index, i -> i, (menuItem, menuItem2) -> menuItem, LinkedHashMap::new));
    }

    public void showMenu(Map<Integer, MenuItem> baseMenu) {
        for (MenuItem item : baseMenu.values()) {
            System.out.println(item.toString());
        }
        System.out.print(">");
    }

    public void runMenu(Map<Integer, MenuItem> baseMenu, Scanner sc) {
        int choice = sc.nextInt();
        while (choice != 0) {
            getMenuItemByChoice(baseMenu, choice).run(sc);
            choice = sc.nextInt();
        }
    }

    public MenuItem getMenuItemByChoice(Map<Integer, MenuItem> baseMenu, Integer choice) {
        return baseMenu.get(choice);
    }
}
