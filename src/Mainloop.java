import menu.MenuAuthentication;
import java.util.Scanner;

public class Mainloop {
    public static void main(String[] args) {
        try (java.util.Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("WELCOME TO HARVARD UNIVERSITY!");
                System.out.println("1. Go to authentication");
                System.out.println("2. Quit");
                System.out.println("Enter your choice:");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        System.out.println("CONNECTION!\n");
                        System.out.println("1. Admin");
                        System.out.println("2. Student");
                        System.out.println("3. Quit ");
                        System.out.println("Enter your authentication type:");

                        MenuAuthentication menuAuthentication = new MenuAuthentication();
                        menuAuthentication.menuAuthentication();
                        
                    case 2:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }
}
