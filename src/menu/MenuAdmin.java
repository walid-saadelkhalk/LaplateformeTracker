package src.menu;

import src.model.AdminRepository;
import java.util.Scanner;

public class MenuAdmin {
    public void menuAdmin() {
        AdminRepository AdminRepository = new AdminRepository();
        Scanner scanner = new Scanner(System.in);

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

        int adminChoice = scanner.nextInt();
        scanner.nextLine(); 

        switch (adminChoice) {
            case 1:
                AdminRepository.searchStudentById();
                break;
            case 2:
                AdminRepository.createAccount();
                break;
            case 3:
                AdminRepository.updateStudent();
                break;
            case 4:
                AdminRepository.deleteStudent();
                break;
            case 5:
                System.out.println("Enter a grade (Feature not yet implemented)");
                break;
            case 6:
                System.out.println("See classes (Feature not yet implemented)");
                break;
            case 7:
                AdminRepository.getAllStudents();
                break;
            case 8:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }
}
