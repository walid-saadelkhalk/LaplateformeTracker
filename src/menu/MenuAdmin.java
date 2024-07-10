package src.menu;

import src.model.AdminRepository;
import java.util.Scanner;

public class MenuAdmin {
    public void menuAdmin() {
        AdminRepository adminRepo = new AdminRepository();
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
        scanner.nextLine(); // Consume newline

        switch (adminChoice) {
            case 1:
                System.out.println("Enter student ID to search:");
                int searchId = scanner.nextInt();
                adminRepo.searchStudentById(searchId);
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
                String email = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                adminRepo.createAccount(firstName, lastName, age, email, password);
                break;
            case 3:
                System.out.println("Enter student ID to update:");
                int updateId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.println("Enter new first name:");
                String newFirstName = scanner.nextLine();
                System.out.println("Enter new last name:");
                String newLastName = scanner.nextLine();
                System.out.println("Enter new age:");
                int newAge = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.println("Enter new email:");
                String newEmail = scanner.nextLine();
                System.out.println("Enter new password:");
                String newPassword = scanner.nextLine();
                adminRepo.updateStudent(updateId, newFirstName, newLastName, newAge, newEmail, newPassword);
                break;
            case 4:
                System.out.println("Enter student ID to delete:");
                int deleteId = scanner.nextInt();
                adminRepo.deleteStudent(deleteId);
                break;
            case 5:
                System.out.println("Enter a grade (Feature not yet implemented)");
                break;
            case 6:
                System.out.println("See classes (Feature not yet implemented)");
                break;
            case 7:
                adminRepo.getAllStudents();
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
