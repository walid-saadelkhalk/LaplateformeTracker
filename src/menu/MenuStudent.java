package src.menu;
import java.util.Scanner;

public class MenuStudent {
    public void menuStudent(Scanner scanner){

        boolean continueStudentLoop = true;
        while (continueStudentLoop) {

            System.out.println("\nWELCOME STUDENT ANDERSON!");
            System.out.println("What do you want to do?");
            System.out.println("1.Maths grades");
            System.out.println("2.Physics grades");
            System.out.println("3.English grades");
            System.out.println("4. Gradebook");
            System.out.println("5. Graphics");
            System.out.println("6. Quit");



            if (scanner.hasNextInt()) {
                int studentChoice = scanner.nextInt();
                scanner.nextLine();

                switch (studentChoice){
                    case 1:
                        System.out.println("Maths grades");
                        break;
                    case 2:
                        System.out.println("Physics grades");
                        break;
                    case 3:
                        System.out.println("English grades");
                        break;
                    case 4:
                        System.out.println("Gradebook");
                        break;
                    case 5:
                        System.out.println("Graphics");
                        break;
                    case 6:
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
