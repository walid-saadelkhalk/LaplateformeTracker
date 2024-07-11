package src;

import src.menu.MenuAuthentication;
import java.util.Scanner;


/*
 * This class is responsible for the main loop of the program.
 * It will prompt the user to choose between authentication and quitting the program.
 * It will then call the respective menu for the user.
 */


public class Mainloop {
    public static void main(String[] args) {
        try (java.util.Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nWELCOME TO HARVARD UNIVERSITY!");
                System.out.println("1. Go to authentication");
                System.out.println("2. Quit");
                System.out.println("Enter your choice:");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        MenuAuthentication menuAuthentication = new MenuAuthentication();
                        menuAuthentication.menuAuthentication(scanner);
                        break;                        
                    case 2:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }
    }
}