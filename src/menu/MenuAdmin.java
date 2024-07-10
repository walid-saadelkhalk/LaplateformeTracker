package menu;
import src.controler.Authentication;
import java.util.Scanner; 

public class MenuAdmin {
    public void menuAdmin(){

        System.out.println("WELCOME ADMIN ANDERSON!\n");
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

        Scanner scanner = new Scanner(System.in);
        int adminChoice = scanner.nextInt();
        scanner.nextLine();
        boolean authenticationman = true;
        switch (adminChoice){
            case 1:
                System.out.println("Search a student");
                break;
            case 2:
                Authentication authentication = new Authentication();
                Authentication.createAccount();
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
            authenticationman = false;
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}
