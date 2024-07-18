package src.menu;

import java.util.Scanner;
import src.model.AdminRepository;
import src.model.User;

public class MenuAdmin {

    public void menuAdmin(Scanner scanner, User user) {
        boolean continueAdminLoop = true;

        while (continueAdminLoop) {
            // printed menu for admin
            System.out.println("\nWELCOME to the ADMIN MENU " + user.getFirstName() + " " + user.getLastName() + " !");
            System.out.println("What do you want to do?");
            System.out.println("1. Search a student");
            System.out.println("2. Create a student");
            System.out.println("3. Update a student");
            System.out.println("4. Delete a student");
            System.out.println("5. Enter a grade");
            System.out.println("6. Export results (CSV, PDF, HTML)"); 
            System.out.println("7. All students");
            System.out.println("8. Quit");
            System.out.println("Enter your choice:");

            if (scanner.hasNextInt()) {
                int adminChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline after reading integer input
            
                switch (adminChoice) {
                    case 1:
                        AdminRepository.searchStudent(scanner); // Example method in AdminRepository for searching a student
                        break;
                    case 2:
                        AdminRepository.createAccount(scanner); // Example method in AdminRepository for creating a student
                        break;
                    case 3:
                        AdminRepository.updateStudent(scanner); // Example method in AdminRepository for updating a student
                        break;
                    case 4:
                        AdminRepository.deleteStudent(scanner); // Example method in AdminRepository for deleting a student
                        break;
                    case 5:
                        AdminRepository.addGrade(scanner); // Example method in AdminRepository for adding a grade
                        break;
                    case 6:
                        exportMenu(scanner); // Call export menu
                        break;
                    case 7:
                        AdminRepository.getAllStudents(scanner); // Example method in AdminRepository for getting all students
                        break;
                    case 8:
                        continueAdminLoop = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            }
        }

        System.out.println("Exiting admin menu. Goodbye!");
    }

    private void exportMenu(Scanner scanner) {
        boolean continueExportMenu = true;

        while (continueExportMenu) {
            System.out.println("\nEXPORT MENU");
            System.out.println("Choose format to export:");
            System.out.println("1. Export to CSV");
            System.out.println("2. Export to HTML");
            System.out.println("3. Back to main menu");
            System.out.println("Enter your choice:");

            if (scanner.hasNextInt()) {
                int exportChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline after reading integer input

                switch (exportChoice) {
                    case 1:
                        AdminRepository.exportStudentsToCSV(); // Example method in AdminRepository for exporting to CSV
                        break;
                    case 2:
                        AdminRepository.exportStudentsToHTML(); // Example method in AdminRepository for exporting to HTML
                        break;
                    case 3:
                        continueExportMenu = false; // Exit the export menu and return to main menu
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            }
        }
    }
}
