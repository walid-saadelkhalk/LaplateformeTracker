// File: src/menu/MenuAdmin.java

package src.menu;

import java.util.Scanner;
import src.model.AdminRepository;

public class MenuAdmin {

    public void menuAdmin(Scanner scanner) {
        boolean continueAdminLoop = true;

        while (continueAdminLoop) {
            System.out.println("\nWELCOME ADMIN ANDERSON!");
            System.out.println("What do you want to do?");
            System.out.println("1. Search a student");
            System.out.println("2. Create a student");
            System.out.println("3. Update a student");
            System.out.println("4. Delete a student");
            System.out.println("5. Enter a grade");
            System.out.println("6. Export results (CSV, PDF, HTML)"); // New option for exporting results
            System.out.println("7. All students");
            System.out.println("8. Quit");
            System.out.println("Enter your choice:");

            if (scanner.hasNextInt()) {
                int adminChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline after reading integer input
            
                switch (adminChoice) {
                    case 1:
                        AdminRepository.searchStudent(scanner);
                        break;
                    case 2:
                        AdminRepository.createAccount(scanner);
                        break;
                    case 3:
                        AdminRepository.updateStudent(scanner);
                        break;
                    case 4:
                        AdminRepository.deleteStudent(scanner);
                        break;
                    case 5:
                        AdminRepository.addGrade(scanner);
                        break;
                    case 6:
                        exportMenu(scanner);
                        break;
                    case 7:
                        AdminRepository.getAllStudents(scanner);
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
            // System.out.println("2. Export to PDF");
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
