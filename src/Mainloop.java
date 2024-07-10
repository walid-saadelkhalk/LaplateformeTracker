package src;

import src.menu.MenuAuthentication;
import src.menu.MenuAdmin;
import java.util.Scanner;

public class Mainloop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("WELCOME TO HARVARD UNIVERSITY!");
                System.out.println("1. Go to authentication");
                System.out.println("2. Admin menu");
                System.out.println("3. Quit");
                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

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
                        return; 
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } finally {
            scanner.close(); 
        }
    }
}
