package src.menu;

import java.util.Scanner;

import src.model.StudentRepository;
import src.model.User;

public class MenuStudent {

    /*
     * This method is responsible for the student menu.
     * It will prompt the user to choose between maths, physics, english, gradebook, or quitting the program.
     */
    public void menuStudent(Scanner scanner, User user) {
        // boolean to keep the loop running
        boolean continueStudentLoop = true;
        StudentRepository studentRepository = new StudentRepository();
        while (continueStudentLoop) {
            // printed menu for student
            System.out.println("\nWELCOME to the STUDENT MENU " + user.getFirstName() + " " + user.getLastName() + " !");
            System.out.println("What do you want to do?");
            System.out.println("1. Maths grades");
            System.out.println("2. Physics grades");
            System.out.println("3. English grades");
            System.out.println("4. Gradebook");
            System.out.println("5. Quit");

            if (scanner.hasNextInt()) {
                int studentChoice = scanner.nextInt();
                scanner.nextLine();

                switch (studentChoice) {
                    case 1:
                        studentRepository.handleMathGrades(scanner); // Example method in StudentRepository for handling math grades
                        break;
                    case 2:
                        studentRepository.handlePhysicsGrades(scanner); // Example method in StudentRepository for handling physics grades  
                        break;
                    case 3:
                        studentRepository.handleEnglishGrades(scanner); // Example method in StudentRepository for handling english grades
                        break;
                    case 4:
                        
                        studentRepository.handleGradebook(scanner); // Example method in StudentRepository for handling gradebook
                        break;
                    case 5:
                    // case 5 to quit the loop
                        System.out.println("wassup");
                        continueStudentLoop = false;
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
