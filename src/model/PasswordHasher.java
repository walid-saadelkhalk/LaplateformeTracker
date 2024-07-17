package src.model;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;

public class PasswordHasher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Original password: " + password);
        System.out.println("Hashed password: " + hashedPassword);
    }
}
