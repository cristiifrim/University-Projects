package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> __commands;
    private static Scanner console = new Scanner(System.in);

    public TextMenu() {
        __commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        __commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for(Command com : __commands.values()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        while(!false) {
            printMenu();
            System.out.print("Input the option: ");
            String key = console.nextLine();
            Command com = __commands.get(key);

            if(com == null) {
                System.out.println("Invalid option");
                continue;
            }
            com.execute();
        }
    }
}
