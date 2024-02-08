
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingListApp {

    private ArrayList<Item> shoppingList;
    private Scanner scan;

    public ShoppingListApp() {
        shoppingList = new ArrayList<>();
        scan = new Scanner(System.in);
    }

    public void start() {

        readData();  // read data in from the file

        System.out.println("---------------------------------");
        System.out.println("Welcome to the shopping list app!");
        System.out.println("---------------------------------");
        String option = "";
        while (!option.equals("q")) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("(v)iew shopping list");
            System.out.println("(a)dd item to list");
            System.out.println("(e)dit an item's price");
            System.out.println("(r)emove item from list");
            System.out.println("(q)uit");
            System.out.print("Enter choice: ");
            option = scan.nextLine();
            if (option.equals("v")) {
                viewShoppingList();
            } else if (option.equals("a")) {
                addItemToList();
            } else if (option.equals("e")) {
                editItemPrice();
            } else if (option.equals("r")) {
                removeItem();
            }
            saveData();
        }
        System.out.println("Goodbye!");
    }

    private void viewShoppingList() {
        if (shoppingList.size() == 0) {
            System.out.println();
            System.out.println("shopping list empty");
        } else {
            System.out.println();
            double totalCost = 0;
            for (Item item : shoppingList) {
                System.out.println(item.getName() + " " + item.getPrice());
                totalCost += item.getPrice();
            }

            System.out.println("\nTotal Items: " + shoppingList.size());
            System.out.println("Total Cost: " + totalCost);
        }
    }

    private void addItemToList() {
        System.out.print("What is the item name? ");
        String name = scan.nextLine();
        System.out.print("What is the item's cost? ");
        try {
            double cost = scan.nextDouble();
            scan.nextLine();
            shoppingList.add(new Item(name, cost));
        } catch(InputMismatchException error) {
            System.out.println("\nThat is not a valid price!");
        }
    }

    private void editItemPrice() {
        System.out.print("What is the item name? ");
        String name = scan.nextLine();
        int idx = -1;

        for (int i = 0; i < shoppingList.size(); i++) {
            if (name.equals(shoppingList.get(i).getName())) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("\nitem not found");
        } else {
            System.out.print("What is the item's new cost? ");
            try {
                double cost = scan.nextDouble();
                scan.nextLine();
                shoppingList.get(idx).setPrice(cost);
            } catch(InputMismatchException error) {
                System.out.println("\nThat is not a valid price!");
            }
        }
    }

    private void removeItem() {
        System.out.print("What is the item name? ");
        String name = scan.nextLine();
        int idx = -1;

        for (int i = 0; i < shoppingList.size(); i++) {
            if (name.equals(shoppingList.get(i).getName())) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("\nitem not found");
        } else {
            shoppingList.remove(idx);
            System.out.println("\nItem: " + name + " has been removed!");
        }
    }

    private void readData() {
        try {
            File myFile = new File("src\\shoppinglist.txt");
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                String name = splitData[0];
                double cost = Double.parseDouble(splitData[1]);
                Item theItem = new Item(name, cost);
                shoppingList.add(theItem);
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void saveData() {
        try {
            PrintWriter dataFile = new PrintWriter("src\\shoppinglist.txt");
            for (Item item : shoppingList) {
                dataFile.println(item.getName() + "," + item.getPrice());
            }
            dataFile.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}