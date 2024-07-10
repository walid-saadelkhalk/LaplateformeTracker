package menu;
import java.util.Scanner;
import menu.MenuAdmin;

public class MenuAuthentication {
    Scanner scanner = new Scanner(System.in);
    public void menuAuthentication(){
        int authentication = scanner.nextInt();
        scanner.nextLine();
        switch (authentication) {
            case 1:

                MenuAdmin menuAdmin = new MenuAdmin();
                menuAdmin.menuAdmin();
                break; 

            case 2:
                System.out.println("WELCOME STUDENT ANDERSON!\n");
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

