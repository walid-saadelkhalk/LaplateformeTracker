package menu;
import java.util.Scanner; 
import src.controler.Authentication;

/*
 * This class is responsible for the menu of the admin.
 * It will prompt the admin to choose between different options.
 * It will then call the respective method from the Authentication class.
 */

public class MenuAdmin {
    public void menuAdmin(Scanner scanner){
        boolean continueAdminLoop = true;
        
        while (continueAdminLoop) {
            System.out.println("\nWELCOME ADMIN ANDERSON!");
            System.out.println("What do you want to do?");
            System.out.println("1. Search a student");
            System.out.println("2. Create a student");
            System.out.println("3. Update a student");
            System.out.println("4. Delete a student");
            System.out.println("5. Enter a grade");
            System.out.println("6. See classes");
            System.out.println("7. All students");
            System.out.println("8. Quit");
            System.out.println("Enter your choice:");

            int adminChoice = scanner.nextInt();
            scanner.nextLine();
            boolean authenticationMan = true;
            
            switch (adminChoice){
                case 1:
                    System.out.println("Search a student");
                    break;
                case 2:
                    Authentication authentication = new Authentication();
                    authentication.createAccount();
                    break;
                case 3:
                    System.out.println("Update a student");
                    break;
                case 4:
                    System.out.println("Delete a student");
                    break;
                case 5:
                    System.out.println("Enter a grade");
                    break;
                case 6:
                    System.out.println("See classes");
                    break;
                case 7:
                    System.out.println("All students");
                    break;
                case 8:
                System.out.println("waddup");
                    continueAdminLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
