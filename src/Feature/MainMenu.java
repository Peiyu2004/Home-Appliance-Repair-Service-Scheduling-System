package Feature;

import java.util.*;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
    	String choice;
        while(true) {
            clearScreen();
            System.out.println("=======================================");
            System.out.println("     Home Appliance Service System");
            System.out.println("=======================================");

            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> registerUser();
                    case "2" -> loginUser();
                    case "3" -> {
                        System.out.println("\nExiting... Goodbye!");
                        return;
                    }
                    default -> {
                        System.out.println("\nInvalid choice.");
                        pause();
                    }
                }
            } else {
                new HomePage(currentUser).showMenu();
            }
        }
    }

    // Registration and Login
    private static void registerUser() {
        clearScreen();
        System.out.println("=== Register ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (customer/technician): ");
        String role = scanner.nextLine().toLowerCase();

        boolean success = User.registerUser(username, password, role);
        if (success) System.out.println("\nUser registered successfully!");
        else System.out.println("\nError: Username already exists.");
        pause();
    }

    private static void loginUser() {
    	
        clearScreen();
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        currentUser = User.loginUser(username, password);
        if (currentUser != null) {
            System.out.println("\nLogin successful!");
        } else {
            System.out.println("\nError: Incorrect username or password.");
        }
        pause();
    }

    public static void logout() {
        currentUser = null;
    }

    // Helpers
    private static void pause() {
        System.out.println("\n(Press ENTER to continue...)");
        scanner.nextLine();
    }

    private static void clearScreen() {
        for (int i = 0; i < 30; i++) System.out.println();
    }
}