package src.menu;

import src.controler.Authentication;
import java.util.Scanner;

public class MenuAuthentication {
    public void menuAuthentication() {
        Authentication authentication = new Authentication();
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME TO AUTHENTICATION MENU\n");
        System.out.println("What do you want to do?");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.println("Enter your choice:");

        int authChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (authChoice) {
            case 1:
                System.out.println("Enter email:");
                String email = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                authentication.login(email, password);
                break;
            case 2:
                System.out.println("Enter first name:");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name:");
                String lastName = scanner.nextLine();
                System.out.println("Enter age:");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.println("Enter email:");
                String regEmail = scanner.nextLine();
                System.out.println("Enter password:");
                String regPassword = scanner.nextLine();
                authentication.register(firstName, lastName, age, regEmail, regPassword);
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}
