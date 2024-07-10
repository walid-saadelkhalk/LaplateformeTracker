package src.menu;

import java.util.Scanner;


import src.menu.MenuStudent;
import src.menu.MenuAdmin;

public class MenuAuthentication {
    Scanner scanner = new Scanner(System.in);

    public void menuAuthentication() {
        System.out.println("\nCONNECTION!");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("3. Quit ");
        System.out.println("Enter your authentication type:");

        int authentication = scanner.nextInt();
        scanner.nextLine();

        switch (authentication) {
            case 1:
                MenuAdmin menuAdmin = new MenuAdmin();
                menuAdmin.menuAdmin();
                break; 
            case 2:
                MenuStudent menuStudent = new MenuStudent();
                menuStudent.menuStudent();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
