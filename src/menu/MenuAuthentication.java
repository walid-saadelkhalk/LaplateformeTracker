package src.menu;
import java.util.Scanner;
import menu.MenuAdmin;
import menu.MenuStudent;

/*
 * This class is responsible for the authentication of the user.
 * It will prompt the user to choose between admin and student.
 * It will then call the respective menu for the user.
 * It will also allow the user to quit the program.
 */

public class MenuAuthentication {
    public void menuAuthentication(Scanner scanner){

        boolean continueAuthenticationLoop = true;

        while (continueAuthenticationLoop) {
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
                    menuAdmin.menuAdmin(scanner);
                    break; 
                case 2:
                    MenuStudent menuStudent = new MenuStudent();
                    menuStudent.menuStudent(scanner);
                    break;
                case 3:
                    System.out.println("Goodbyeeeeee!");
                    continueAuthenticationLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}

