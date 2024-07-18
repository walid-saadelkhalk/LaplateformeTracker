package src.menu;

import java.util.Scanner;
import src.model.Connecting;
import src.model.User;

public class MenuAuthentication {
    public void menuAuthentication(Scanner scanner) {
        boolean continueAuthenticationLoop = true;

        while (continueAuthenticationLoop) {
            // printed menu for authentication
            System.out.println("\nCONNECTION!");
            System.out.println("1. Admin");
            System.out.println("2. Student");
            System.out.println("3. Quit ");
            System.out.println("Enter your authentication type:");

            if (scanner.hasNextInt()) {
                int authentication = scanner.nextInt();
                scanner.nextLine();

                switch (authentication) {
                    // case 1 for admin menu 
                    case 1:
                        boolean authenticatedAdmin = false;
                        int attemptsAdmin = 0;

                        while (!authenticatedAdmin && attemptsAdmin < 3) {
                            User adminUser = Connecting.AdminConnection(scanner);
                            if (adminUser != null) {
                                MenuAdmin menuAdmin = new MenuAdmin();
                                menuAdmin.menuAdmin(scanner , adminUser);
                                authenticatedAdmin = true;
                            } else {
                                attemptsAdmin++;
                                System.out.println("Connection failed. Please try again. Attempt " + attemptsAdmin + " of 3.");
                            }
                        }
                        if (!authenticatedAdmin) {
                            System.out.println("Too many failed attempts. Returning to authentication menu.");
                        }
                        break;
                    // case 2 for student menu
                    case 2:
                        boolean authenticatedStudent = false;
                        int attemptsStudent = 0;

                        while (!authenticatedStudent && attemptsStudent < 3) {
                            User studentUser = Connecting.StudentConnection(scanner);
                            if (studentUser != null) {
                                MenuStudent menuStudent = new MenuStudent();
                                menuStudent.menuStudent(scanner, studentUser);
                                authenticatedStudent = true;
                            } else {
                                attemptsStudent++;
                                System.out.println("Connection failed. Please try again. Attempt " + attemptsStudent + " of 3.");
                            }
                        }
                        if (!authenticatedStudent) {
                            System.out.println("Too many failed attempts. Returning to authentication menu.");
                        }
                        break;
                    // case 3 for quit the program
                    case 3:
                        System.out.println("Goodbyeeeeee!");
                        continueAuthenticationLoop = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
