package src.menu;

import java.util.Scanner;

public class MenuStudent {
    public void menuStudent() {
        System.out.println("WELCOME STUDENT ANDERSON!\n");
        System.out.println("What do you want to do?");
        System.out.println("1. Maths grades");
        System.out.println("2. Physics grades");
        System.out.println("3. English grades");
        System.out.println("4. Gradebook");
        System.out.println("5. Graphics");
        System.out.println("6. Quit");

        Scanner scanner = new Scanner(System.in);
        try {
            int studentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (studentChoice) {
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
                    System.out.println("Goodbye!");
                    return; // Exit the method
                default:
                    System.out.println("Invalid choice.");
            }
        } finally {
            scanner.close(); // Ensure the scanner is closed
        }
    }
}
