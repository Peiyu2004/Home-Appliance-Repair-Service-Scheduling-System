package Feature;

import java.util.Scanner;

public class HomePage {
    private User currentUser;
    private Scanner scanner;
    private String role;

    public HomePage(User currentUser) {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        this.role = currentUser.getRole().toLowerCase();
    }

    public void showMenu() {
    	String choice;
        do {
            System.out.println("\n=======================================");
            System.out.println("             Home Page");
            System.out.println("=======================================");
            System.out.println("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
            
            if (role.equals("customer") || role.equals("technician"))
            {
            	System.out.println("1. Profile Management");
            	System.out.println("2. Appliance Management");
            	System.out.println("3. Repair Slot Management");
            	System.out.println("4. Appointment Management");
            	System.out.println("5. Logout");
            }
            else
            {
            	System.out.println("1. Profile Management");
            	System.out.println("2. Appliance Management");
            	System.out.println("3. Repair Slot Management");
            	System.out.println("4. Appointment Management");
            	System.out.println("5. Admin Menu");
                System.out.println("6. Logout");
            }
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            if (role.equals("customer") || role.equals("technician")) 
            {
            	switch (choice) 
            	{
                case "1" -> new UserMenu(currentUser).showMenu();
                case "2" -> new ApplianceMenu(currentUser).showMenu();
                case "3" -> new RepairSlotMenu(currentUser).showMenu();
                case "4" -> new AppointmentMenu(currentUser).showMenu();
                case "5" -> {
                    System.out.println("Logging out...");
                    MainMenu.logout();
                }
                default -> System.out.println("Invalid choice. Try again.");
                }
            }
            else if (role.equals("admin"))
            {
            	switch (choice) 
            	{
                case "1" -> new UserMenu(currentUser).showMenu();
                case "2" -> new ApplianceMenu(currentUser).showMenu();
                case "3" -> new RepairSlotMenu(currentUser).showMenu();
                case "4" -> new AppointmentMenu(currentUser).showMenu();
                case "5" -> new AdminMenu(currentUser).showMenu();
                case "6" -> {
                    System.out.println("Logging out...");
                    MainMenu.logout();
                }
                default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }while (!exitCondition(choice));
    }

    private boolean exitCondition(String choice) {
        if (role.equals("customer") || role.equals("technician")) {
            return choice.equals("5"); // logout option
        } else if (role.equals("admin")) {
            return choice.equals("6"); // logout option
        }
        return false;
    }
}