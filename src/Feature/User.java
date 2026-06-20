package Feature;

import java.io.*;
import java.util.*;

public class User {
    private String username;
    private String password;
    private String role;
    private static final String USER_FILE = "users.txt";

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    //File Handling 
    @Override
    public String toString() {
        return username + "," + password + "," + role;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        return new User(parts[0], parts[1], parts[2]);
    }

    private static List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            // ignore if file not found
        }
        return users;
    }

    private static void writeUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // User Management Methods 
    public static boolean registerUser(String username, String password, String role) {
        List<User> users = readUsersFromFile();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // already exists
            }
        }
        users.add(new User(username, password, role));
        writeUsersToFile(users);
        return true;
    }

    public static User loginUser(String username, String password) {
        List<User> users = readUsersFromFile();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void updateProfile(String newUsername, String newPassword, String newRole) {
        List<User> users = readUsersFromFile();
        for (User u : users) {
            if (u.getUsername().equals(this.username)) {
                u.setUsername(newUsername);
                u.setPassword(newPassword);
                u.setRole(newRole);
                this.username = newUsername;
                this.password = newPassword;
                this.role = newRole;
                break;
            }
        }
        writeUsersToFile(users);
    }

    public boolean deleteProfile() {
        List<User> users = readUsersFromFile();
        boolean removed = users.removeIf(u -> u.getUsername().equals(this.username));
        if (removed) {
            writeUsersToFile(users);
        }
        return removed;
    }

    public static List<User> getAllUsers() {
        return readUsersFromFile();
    }
    
    public static User findUser(String username) {
        List<User> users = readUsersFromFile();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null; // not found
    }

    public static boolean deleteUserByAdmin(String username) {
        List<User> users = readUsersFromFile();
        boolean removed = users.removeIf(u -> u.getUsername().equals(username));
        if (removed) {
            writeUsersToFile(users);
        }
        return removed;
    }
}
