package Feature;

import java.util.Scanner;

public class UserMenu {
    private User currentUser;
    private static Scanner scanner = new Scanner(System.in);

    public UserMenu(User currentUser) {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Profile ===");
            System.out.println("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Delete Profile");
            System.out.println("4. Back to Home");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": 
                	viewProfile();
                	break;
                case "2": 
                	updateProfile();
                	break;
                case "3":
                	if (deleteOwnProfile()) 
                        return; // if deleted, user logs out
                case "4":
                	return; // back to home menu
                default: 
                	System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProfile() {
    	clearScreen();
        System.out.println("\n=== Your Profile ===");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Role: " + currentUser.getRole());
        pause();
    }

    private void updateProfile() {
    	clearScreen();
        System.out.println("\n=== Update Profile ===");
        System.out.print("New username (leave blank to keep current): ");
        String newUsername = scanner.nextLine();
        if (newUsername.isEmpty()) newUsername = currentUser.getUsername();

        System.out.print("New password (leave blank to keep current): ");
        String newPassword = scanner.nextLine();
        if (newPassword.isEmpty()) newPassword = currentUser.getPassword();

        System.out.print("New role (leave blank to keep current): ");
        String newRole = scanner.nextLine().toLowerCase();
        if (newRole.isEmpty()) newRole = currentUser.getRole();

        currentUser.updateProfile(newUsername, newPassword, newRole);
        System.out.println("Profile updated successfully!");
        
        pause();
    }

    private boolean deleteOwnProfile() {
    	clearScreen();
        System.out.println("\n=== Delete Profile ===");
        System.out.print("Confirm password: ");
        String password = scanner.nextLine();

        if (!currentUser.getPassword().equals(password)) {
            System.out.println("Error: Incorrect password.");
            return false;
        }

        if (currentUser.deleteProfile()) {
            System.out.println("Your profile has been deleted. Logging out...");
            MainMenu.logout(); // ensure logout
            return true;
        } else {
            System.out.println("Error deleting profile.");
            return false;
        }
    }
    
    // to make system user friendly
    private static void pause() {
        System.out.println("\n(Press ENTER to continue...)");
        scanner.nextLine();
    }

    private static void clearScreen() {
        for (int i = 0; i < 30; i++) System.out.println();
    }
}