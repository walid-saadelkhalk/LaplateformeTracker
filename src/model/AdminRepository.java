package src.model;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRepository {

    /*
     * Admin functions
     * 1. Create a new student
     * 2. Update a student
     * 3. Delete a student
     * 4. Get all students
     * 5. Search a student
     * 6. Add a grade
     * 7. Export students to CSV
     * 8. Export students to HTML
     * 9. Update average grade
     * 10. Get unique student ID
     * 11. Get unique gradebook ID
     * 12. Get unique email
     * 13. Validate password
     * 14. Hash password
     * 15. Read password
     * 16. Get positive integer input
     * 17. Get unique integer input
     * 18. Get unique email input
     * 19. Get unique gradebook ID input
     * This Class is in charge of most of the code the all application works around it 
     */
    // Function to create a new student in TABLES Student and Grade_book
    public static void createAccount(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        while (!firstName.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter a valid first name (letters only):");
            firstName = scanner.nextLine();
        }
    
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        while (!lastName.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter a valid last name (letters only):");
            lastName = scanner.nextLine();
        }
        lastName = lastName.toUpperCase();
    
        int age = getPositiveIntInput(scanner, "Enter age (must be a positive integer):");
    
        int studentId = getUniqueIntInput(scanner, "Enter student ID (must be a strictly positive integer):");
    
        int gradebookId = getUniqueGradebookIdInput(scanner, "Enter gradebook ID (must be a strictly positive integer):");
    
        String email = getUniqueEmailInput(scanner, "Enter email (must end with @harvard.com and contain letters):");
    
        String password;
        while (true) {
            password = readPassword("Enter password (minimum 8 characters, at least one special character, one uppercase letter, one lowercase letter and one number):");
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please ensure it meets the criteria.");
            }
        }
    
        // Encrypt or hash data and password
        String encryptedFirstName = Crypto.encrypt(firstName);
        String encryptedLastName = Crypto.encrypt(lastName);
        String encryptedEmail = Crypto.encrypt(email);
        String hashedPassword = hashPassword(password);
    
        String insertStudentSql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertGradeBookSql = "INSERT INTO Grade_book (ID_student) VALUES (?)";
    
        try (Connection connection = Database.getConnection();
             PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql);
             PreparedStatement insertGradeBookStmt = connection.prepareStatement(insertGradeBookSql)) {
    
            // Insert into Student table
            insertStudentStmt.setString(1, encryptedFirstName);
            insertStudentStmt.setString(2, encryptedLastName);
            insertStudentStmt.setInt(3, age);
            insertStudentStmt.setInt(4, studentId);
            insertStudentStmt.setInt(5, gradebookId);
            insertStudentStmt.setString(6, encryptedEmail);
            insertStudentStmt.setString(7, hashedPassword);
    
            int studentRowsAffected = insertStudentStmt.executeUpdate();
    
            insertGradeBookStmt.setInt(1, studentId);
    
            int gradeBookRowsAffected = insertGradeBookStmt.executeUpdate();
    
            if (studentRowsAffected > 0 && gradeBookRowsAffected > 0) {
                System.out.println("Student account and grade book entry created successfully!");
            } else {
                System.out.println("Failed to create student account or grade book entry.");
            }
        
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("An error occurred: The email already exists. Please try again with a different value.");
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the student account or grade book entry.");
            e.printStackTrace();
        }
    }
    
    // Function to Update a student in TABLES Student and Grade_book
    public static void updateStudent(Scanner scanner) {
        System.out.println("Enter ID to update:");
        int studentId = getPositiveIntInput(scanner, "Enter student ID:");
    
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        while (!firstName.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter a valid first name (letters only):");
            firstName = scanner.nextLine();
        }
    
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        while (!lastName.matches("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter a valid last name (letters only):");
            lastName = scanner.nextLine();
        }
        lastName = lastName.toUpperCase();
    
        int age = getPositiveIntInput(scanner, "Enter age (must be a positive integer):");
    
        String email = getUniqueEmailInput(scanner, "Enter email (must end with @harvard.com and contain letters):");
    
        String password;
        while (true) {
            password = readPassword("Enter password (minimum 8 characters, at least one special character, one uppercase letter, one lowercase letter and one number):");
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please ensure it meets the criteria.");
            }
        }
    
        // Encrypt or hash data
        String encryptedFirstName = Crypto.encrypt(firstName);
        String encryptedLastName = Crypto.encrypt(lastName);
        String encryptedEmail = Crypto.encrypt(email);
        String hashedPassword = hashPassword(password);
        
        // Update Student table
        String sql = "UPDATE Student SET First_name = ?, Last_name = ?, Age = ?, Mail = ?, Password = ? WHERE ID = ?";
        
        
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
    
            stmt.setString(1, encryptedFirstName);
            stmt.setString(2, encryptedLastName);
            stmt.setInt(3, age);
            stmt.setString(4, encryptedEmail);
            stmt.setString(5, hashedPassword);
            stmt.setInt(6, studentId);
    
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating the student.");
            e.printStackTrace();
        }
    }

    // Function to delete a student in TABLES Student and Grade_book
    public static void deleteStudent(Scanner scanner) {
        System.out.println("Enter student ID to delete:");
        String studentId = scanner.nextLine();

        // Delete from Grade_book first
        String deleteGradeBookSql = "DELETE FROM Grade_book WHERE ID_student = ?";
        String deleteStudentSql = "DELETE FROM Student WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmtGradeBook = connection.prepareStatement(deleteGradeBookSql);
             PreparedStatement stmtStudent = connection.prepareStatement(deleteStudentSql)) {

            stmtGradeBook.setString(1, studentId);
            int rowsAffectedGradeBook = stmtGradeBook.executeUpdate();
            System.out.println("Rows deleted from Grade_book: " + rowsAffectedGradeBook);
            // Then delete from Student
            stmtStudent.setString(1, studentId);
            int rowsAffectedStudent = stmtStudent.executeUpdate();
            System.out.println("Rows deleted from Student: " + rowsAffectedStudent);
            if (rowsAffectedStudent > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to get all students from TABLES Student and Grade_book
    public static void getAllStudents(Scanner scanner) {
        String sql = "SELECT * FROM Student";
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");

                // Dcrypt the necessary fields
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);

                System.out.println("ID: " + id + "\nName: " + firstName + " " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to search a student in TABLES Student and Grade_book
    public static void searchStudent(Scanner scanner) {
        System.out.println("Enter search criteria (id, firstname, lastname, age):");
        String searchBy = scanner.nextLine().toLowerCase();

        // Validate search criteria
        String sql = "";
        switch (searchBy) {
            case "id":
                sql = "SELECT * FROM Student WHERE ID = ?";
                break;
            case "firstname":
                sql = "SELECT * FROM Student WHERE First_name = ?";
                break;
            case "lastname":
                sql = "SELECT * FROM Student WHERE Last_name = ?";
                break;
            case "age":
                sql = "SELECT * FROM Student WHERE Age = ?";
                break;
            default:
                System.out.println("Invalid search criteria.");
                return;
        }

        System.out.println("Enter search value:");
        String searchValue = scanner.nextLine();

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Bind the search value based on the search criteria
            if ("id".equals(searchBy) || "age".equals(searchBy)) {
                int intValue = Integer.parseInt(searchValue);
                stmt.setInt(1, intValue);
            } else {
                // Encrypt the search value if it is firstname or lastname
                if ("firstname".equals(searchBy) || "lastname".equals(searchBy)) {
                    searchValue = Crypto.encrypt(searchValue);
                }
                stmt.setString(1, searchValue);
            }

            ResultSet rs = stmt.executeQuery();

            boolean found = false; // Flag to check if any results were found

            while (rs.next()) {
                found = true; // Set flag to true if we find any results
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");

                // Decrypt the necessary fields
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);

                System.out.println("ID: " + id + "\nFirstname: " + firstName + "\nLastname: " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password);
            }

            // If no results were found
            if (!found) {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields.");
        }
    }

    // Function to add a grade to a student in TABLES Grade_book
    public static void addGrade(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();

        if (!studentExists(studentId)) {
            System.out.println("Student not found.");
            scanner.nextLine(); // consume newline
        }

        String subjectColumn = null;
        while (subjectColumn == null) {
            System.out.println("Select subject (1: Math, 2: Physics, 3: English):");
            try {
                int subject = scanner.nextInt();
                scanner.nextLine(); // consume newline
                // Set the subject column based on the user's choice
                switch (subject) {
                    case 1:
                        subjectColumn = "Math_grades";
                        break;
                    case 2:
                        subjectColumn = "Physics_grades";
                        break;
                    case 3:
                        subjectColumn = "English_grades";
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid subject.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
                scanner.nextLine(); // consume invalid input
            }
        }

        // Get the grade to add
        int grade = -1;
        while (grade < 0) {
            System.out.println("Enter grade to add (1 to 100):");
            try {
                grade = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Validate the grade
                if (grade < 1 || grade > 100) {
                    System.out.println("Invalid grade. Please enter a grade between 1 and 100.");
                    grade = -1; // reset grade to continue the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid grade (1 to 100).");
                scanner.nextLine(); // consume invalid input
            }
        }

        // Add the grade to the student's grade book
        try (Connection connection = Database.getConnection()) {
            String selectSql = "SELECT " + subjectColumn + " FROM Grade_book WHERE ID_student = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setString(1, studentId);
            ResultSet rs = selectStmt.executeQuery();

            int currentGrade = 0;
            if (rs.next()) {
                currentGrade = rs.getInt(subjectColumn);
            }

            int newGrade = (currentGrade + grade)/2;

            // Update the grade in the Grade_book table
            String updateSql = "UPDATE Grade_book SET " + subjectColumn + " = ? WHERE ID_student = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, newGrade);
            updateStmt.setString(2, studentId);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully!");
            } else {
                System.out.println("Failed to add grade.");
            }

            updateAverageGrade(studentId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function to update the average grade in TABLES Grade_book and classroom
    public static void updateAverageGrade(String studentId) {
        String sqlSelect = "SELECT Math_grades, Physics_grades, English_grades FROM Grade_book WHERE ID_student = ?";
        String sqlUpdate = "UPDATE Grade_book SET average = ? WHERE ID_student = ?";
        String sqlCheckAverage = "SELECT COUNT(*) FROM classroom WHERE Average_classes = ?";
        String sqlInsertAverage = "INSERT INTO classroom (Average_classes) VALUES (?)";
    
        try (Connection connection = Database.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
             PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate);
             PreparedStatement checkAverageStmt = connection.prepareStatement(sqlCheckAverage);
             PreparedStatement insertAverageStmt = connection.prepareStatement(sqlInsertAverage)) {
    
            selectStmt.setString(1, studentId);
            ResultSet rs = selectStmt.executeQuery();
    
            if (rs.next()) {
                int mathGrade = rs.getInt("Math_grades");
                int physicsGrade = rs.getInt("Physics_grades");
                int englishGrade = rs.getInt("English_grades");
    
                // Calculate the average, considering only non-zero grades
                int total = 0;
                int count = 0;
    
                if (mathGrade > 0) {
                    total += mathGrade;
                    count++;
                }
                if (physicsGrade > 0) {
                    total += physicsGrade;
                    count++;
                }
                if (englishGrade > 0) {
                    total += englishGrade;
                    count++;
                }
    
                if (count > 0) {
                    double average = (double) total / count;
    
                    // Check if the average exists in the classroom table
                    checkAverageStmt.setDouble(1, average);
                    ResultSet rsCheck = checkAverageStmt.executeQuery();
                    if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                        // Average does not exist, insert it
                        insertAverageStmt.setDouble(1, average);
                        insertAverageStmt.executeUpdate();
                    }
    
                    // Update the average in the Grade_book table
                    updateStmt.setDouble(1, average);
                    updateStmt.setString(2, studentId);
    
                    updateStmt.executeUpdate();
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Function to check if a student exists in TABLES Grade_book
    public static boolean studentExists(String studentId) {
        String sql = "SELECT COUNT(*) FROM Grade_book WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function to get a positive integer input
    private static int getPositiveIntInput(Scanner scanner, String prompt) {
        int input;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        return input;
    }

    private static String getEmailInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.endsWith("@harvard.com")) {
                break;
            } else {
                System.out.println("Invalid email. Please enter an email ending with @harvard.com.");
            }
        }
        return input;
    }

    private static String getPasswordInput(String prompt) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Using Scanner for password input.");
            Scanner scanner = new Scanner(System.in);
            return getPasswordFromScanner(scanner, prompt);
        }

        String input;
        while (true) {
            char[] passwordArray = console.readPassword(prompt);
            input = new String(passwordArray);
            if (validatePassword(input)) {
                break;
            } else {
                System.out.println("Invalid password. Please enter a password with a minimum of 8 characters, at least one special character, and one uppercase letter.");
            }
        }
        return input;
    }

    // Function to get a password from a scanner
    private static String getPasswordFromScanner(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (validatePassword(input)) {
                break;
            } else {
                System.out.println("Invalid password. Please enter a password with a minimum of 8 characters, at least one special character, and one uppercase letter.");
            }
        }
        return input;
    }

    // Function to export students to CSV
    public static void exportStudentsToCSV() {
        String csvFilePath = "export/students.csv";
    
        String sql = "SELECT * FROM Student";
    
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter csvWriter = new FileWriter(csvFilePath)) {
    
            // Write CSV header
            csvWriter.append("ID,First_name,Last_name,Age,Email,Password\n");
    
            // Write CSV rows
            while (rs.next()) {
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");
    
                // Déchiffrer les champs nécessaires
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);
    
                csvWriter.append(String.join(",", String.valueOf(id), firstName, lastName,
                        String.valueOf(age), email, password));
                csvWriter.append("\n");
            }
    
            System.out.println("CSV file exported successfully to: " + csvFilePath);
    
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }       

    // Function to export students to HTML
    public static void exportStudentsToHTML() {
        String htmlFilePath = "export/students.html";
    
        String sql = "SELECT * FROM Student";
    
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter htmlWriter = new FileWriter(htmlFilePath)) {
    
            htmlWriter.write("<html>\n<head>\n<title>Students List</title>\n</head>\n<body>\n");
            htmlWriter.write("<h1>Students List</h1>\n<table border=\"1\">\n");
            htmlWriter.write("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Age</th><th>Email</th><th>Password</th></tr>\n");
    
            while (rs.next()) {
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");
    
                
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);
    
                htmlWriter.write("<tr><td>" + id + "</td><td>" + firstName + "</td><td>" + lastName + "</td><td>" + age + "</td><td>" + email + "</td><td>" + password + "</td></tr>\n");
            }
    
            htmlWriter.write("</table>\n</body>\n</html>");
    
            System.out.println("HTML file exported successfully to: " + htmlFilePath);
    
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    // Function to get a password from the console or scanner
    private static String readPassword(String prompt) {
        Console console = System.console();
        if (console == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(prompt);
            return scanner.nextLine();
        }
        char[] passwordArray = console.readPassword(prompt);
        return new String(passwordArray);
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9 ]");

        Matcher hasLowerCase = lowerCasePattern.matcher(password);
        Matcher hasUpperCase = upperCasePattern.matcher(password);
        Matcher hasDigit = digitPattern.matcher(password);
        Matcher hasSpecialChar = specialCharPattern.matcher(password);
        return hasLowerCase.find() && hasUpperCase.find() && hasDigit.find() && hasSpecialChar.find();
    }

    // Function to hash a password using SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Function to get a unique integer input
    private static int getUniqueIntInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value > 0 && isUniqueStudentId(value)) {
                    return value;
                } else {
                    System.out.println("Value must be a strictly positive integer and unique. Please enter a different value.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
    }
    
    // Function to get a unique student ID input
    private static boolean isUniqueStudentId(int studentId) {
        String sql = "SELECT COUNT(*) FROM Student WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Function to get a unique gradebook ID input
    private static int getUniqueGradebookIdInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value > 0 && isUniqueGradebookId(value)) {
                    return value;
                } else {
                    System.out.println("Value must be a strictly positive integer and unique. Please enter a different value.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
        }
    }

    // Function to check if a gradebook ID is unique
    private static boolean isUniqueGradebookId(int gradebookId) {
        String sql = "SELECT COUNT(*) FROM Student WHERE ID_gradebook = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gradebookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Function to get a unique email input
    private static boolean isUniqueEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Student WHERE Mail = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }    
    

    // Function to get a unique email input
    private static String getUniqueEmailInput(Scanner scanner, String prompt) {
        String email;
        while (true) {
            System.out.println(prompt);
            email = scanner.nextLine().toLowerCase();
            if (email.matches(".*[a-zA-Z].*") && email.endsWith("@harvard.com") && isUniqueEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email. Please ensure it contains letters, ends with @harvard.com, and is unique.");
            }
        }
        return email;
    }    
}
