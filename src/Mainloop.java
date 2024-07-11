package src;

import src.menu.MenuAuthentication;
import src.menu.MenuAdmin;
import src.menu.MenuStudent;
import java.util.Scanner;

public class Mainloop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("WELCOME TO HARVARD UNIVERSITY!");
            System.out.println("1. Go to authentication");
            System.out.println("2. Admin menu");
            System.out.println("3. Quit");
            System.out.println("Enter your choice:");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        MenuAuthentication menuAuthentication = new MenuAuthentication();
                        menuAuthentication.menuAuthentication();
                        break;
                    case 2:
                        MenuAdmin menuAdmin = new MenuAdmin();
                        menuAdmin.menuAdmin();
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        scanner.close(); // Close the scanner before exiting
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Input error: " + e.getMessage());
                break; // Exit the loop on input error
            }
        }
    }
}