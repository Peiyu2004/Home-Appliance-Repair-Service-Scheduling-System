package Feature;

public class Admin extends User {

    // Constructor
    public Admin(String username, String password, String role) {
        super(username, password, role);
    }

    // Admin-specific methods (currently managed by AdminMenu)
    public void manageUsers() {
        System.out.println("Admin " + getUsername() + " is managing users...");
    }
}