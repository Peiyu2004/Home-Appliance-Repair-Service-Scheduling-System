package Feature;

import java.util.List;

public class AdminService {

    /**
     * View all registered users
     */
    public void viewAllUsers() {
        System.out.println("=== All Registered Users ===");
        List<User> users = User.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User u : users) {
                System.out.println("- " + u.getUsername() + " (" + u.getRole() + ")");
            }
        }
    }

    /**
     * Delete a user by username
     * @param username the user to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteUserByAdmin(String username) {
        return User.deleteUserByAdmin(username);
    }
    
    // Remove technician specifically
    public boolean removeTechnician(String username) {
        User user = User.findUser(username);
        if (user != null && user.getRole().equalsIgnoreCase("technician")) {
            return User.deleteUserByAdmin(username);
        }
        return false;
    }
}