package src.menu;
<<<<<<< HEAD
import java.util.Scanner; 
import src.model.AdminRepository;

/*
 * This class is responsible for the menu of the admin.
 * It will prompt the admin to choose between different options.
 * It will then call the respective method from the Authentication class.
 */
=======

import java.util.Scanner;
import src.controler.Authentication;
import src.model.AdminRepository;
>>>>>>> dev

public class MenuAdmin {
    public void menuAdmin(Scanner scanner) {
        boolean continueAdminLoop = true;
<<<<<<< HEAD
        AdminRepository adminRepository = new AdminRepository();
        
=======

>>>>>>> dev
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

<<<<<<< HEAD
            int adminChoice = scanner.nextInt();
            scanner.nextLine();
            boolean authenticationMan = true;
            
            switch (adminChoice){
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
                    System.out.println("Enter a grade");
                    break;
                case 6:
                    System.out.println("See classes");
                    break;
                case 7:
                    AdminRepository.getAllStudents();
                    break;
                case 8:
                System.out.println("waddup");
                    continueAdminLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
=======
            if (scanner.hasNextInt()) {
                int adminChoice = scanner.nextInt();
                scanner.nextLine();

                switch (adminChoice) {
                    case 1:
                        System.out.println("Search a student");
                        break;
                    case 2:
                        Authentication.createAccount(scanner);
                        System.out.println("Student created successfully!");
                        break;
                    case 3:
                        AdminRepository.updateStudent(scanner);
                        System.out.println("Student updated successfully!");
                        break;
                    case 4:
                        AdminRepository.deleteStudent(scanner);
                        System.out.println("Student deleted successfully!");
                        break;
                    case 5:
                        System.out.println("Enter a grade");
                        break;
                    case 6:
                        System.out.println("See classes");
                        break;
                    case 7:
                    System.out.println("All students:\n");
                        AdminRepository.getAllStudents(scanner);
                        break;
                    case 8:
                        System.out.println("waddup");
                        continueAdminLoop = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
>>>>>>> dev
            }
        }
    }
}
