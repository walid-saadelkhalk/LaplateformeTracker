package src.menu;

import java.util.Scanner;

import src.model.StudentRepository;
import src.model.User;

public class MenuStudent {
    public void menuStudent(Scanner scanner, User user) {
        boolean continueStudentLoop = true;
        StudentRepository studentRepository = new StudentRepository();
        while (continueStudentLoop) {
            System.out.println("\nWELCOME to the STUDENT MENU " + user.getFirstName() + " " + user.getLastName() + " !");
            System.out.println("What do you want to do?");
            System.out.println("1. Maths grades");
            System.out.println("2. Physics grades");
            System.out.println("3. English grades");
            System.out.println("4. Gradebook");
            System.out.println("5. Graphics");
            System.out.println("6. Quit");

            if (scanner.hasNextInt()) {
                int studentChoice = scanner.nextInt();
                scanner.nextLine();

                switch (studentChoice) {
                    case 1:
                        studentRepository.handleMathGrades(scanner);
                        break;
                    case 2:
                        studentRepository.handlePhysicsGrades(scanner);
                        break;
                    case 3:
                        studentRepository.handleEnglishGrades(scanner);
                        break;
                    case 4:
                        
                        studentRepository.handleGradebook(scanner);
                        break;
                    // case 5:
                    //     System.out.println("Graphics");
                    //     break;
                    case 5:
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
