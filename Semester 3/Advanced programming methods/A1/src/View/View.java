package View;

import Controller.Controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import Model.Vegetable;

public class View {

    private final Controller service;
    private static final Scanner console = new Scanner(System.in);

    public View(Controller srv) {
        this.service = srv;
    }

    private void Menu() {
        System.out.println("--------------------");
        System.out.println("1. Add a vegetable");
        System.out.println("2. Remove a vegetable");
        System.out.println("3. Print vegetables");
        System.out.println("4. Print vegetables that have their weight greater than 0.2 kilograms");
        System.out.println("5. Exit");
        System.out.println("--------------------");
    }

    private void addVeggie() throws Exception {
        System.out.println("Please enter the type of the vegetable[tomato, pepper, eggplant]: ");
        String type = console.nextLine().toLowerCase();
        System.out.println("Please enter the weight of the vegetable[>0]: ");
        float size = console.nextFloat();

        this.service.add(type, size);
        System.out.println("Your vegetable has been added successfully");

    }

    private void removeVeggie() {
        System.out.println("Please enter the index specified in the 3rd option: ");
        int index = console.nextInt();

        this.service.remove(index - 1);

        System.out.println("The vegetable with index " + index + " has been successfully removed.");
    }

    private void printVeggies() throws Exception {
        Vegetable[] veggies = this.service.getVegetables();
        int counter = 0;
        for(Vegetable veggie : veggies) {
            if(veggie != null) {
                System.out.println(++counter + ". " + veggie);
            }
        }
    }

    private void printFilteredVeggies() throws Exception {
        Vegetable[] veggies = this.service.getVegetablesFilteredBySize((float) 0.2);
        int counter = 0;
        for(Vegetable veggie : veggies) {
            if(veggie != null)
                System.out.println(++counter + ". " + veggie.toString());
        }
    }

    public void run() {
        System.out.println("Hello user");

        while(true) {
            this.Menu();
            try {
                int option;
                System.out.println("Please select what do you want to do: ");
                option = console.nextInt();
                console.nextLine();
                switch(option) {
                    case 5:
                        System.out.println("Goodbye!");
                        return;
                    case 1:
                        this.addVeggie();
                        break;
                    case 2:
                        this.removeVeggie();
                        break;
                    case 3:
                        this.printVeggies();
                        break;
                    case 4:
                        this.printFilteredVeggies();
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }
            catch(InputMismatchException e) {
                console.nextLine();
                System.out.println("Please enter a number, not a string.");
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
