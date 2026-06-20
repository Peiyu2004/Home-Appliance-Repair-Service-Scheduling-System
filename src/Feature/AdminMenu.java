package Feature;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private User currentUser;
    private Scanner scanner;
    private AdminService adminService;

    public AdminMenu(User currentUser) {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        this.adminService = new AdminService();
    }

    public void showMenu() {
    	String choice;
        while (true) {
            System.out.println("\n=== Admin User Management Menu ===");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. Remove Technician");
            System.out.println("4. Back to Home");
            System.out.print("Enter your choice: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1": 
                	adminService.viewAllUsers();
                	break;
                case "2": 
                	deleteUserByAdmin();
                	break;
                case "3": 
                	removeTechnician();
                	break;
                case "4": 
                {
                    System.out.println("\nReturning to home...");
                    return;
                }
                default: System.out.println("Invalid choice. Please enter between 1-4.");
            }
        }
    }

    private void deleteUserByAdmin() {
        System.out.println("\n=== Delete User ===");
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();

        if (User.deleteUserByAdmin(username)) {
            System.out.println("User " + username + " deleted successfully!");
        } else {
            System.out.println("Error: User not found.");
        }
    }

    private void removeTechnician() {
        System.out.println("\n=== Remove Technician ===");
        System.out.print("Enter technician username to remove: ");
        String username = scanner.nextLine();

        User user = User.findUser(username);
        if (user != null && user.getRole().equals("technician")) {
            if (User.deleteUserByAdmin(username)) {
                System.out.println("Technician " + username + " removed successfully!");
            } else {
                System.out.println("Error removing technician.");
            }
        } else {
            System.out.println("Error: User not found or not a technician.");
        }
    }
}