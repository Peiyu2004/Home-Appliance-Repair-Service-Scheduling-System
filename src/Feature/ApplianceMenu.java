package Feature;

import java.util.Scanner;

public class ApplianceMenu {
    private ApplianceService applianceService;
    private Scanner scanner;
    private User currentUser; // logged-in user
    private String role;

    public ApplianceMenu(User currentUser) {
        this.applianceService = new ApplianceService();
        this.scanner = new Scanner(System.in);
        this.currentUser = currentUser; // save logged-in user
        this.role = currentUser.getRole().toLowerCase();
    }

    public void showMenu() {
    	int choice;
    	do {
            System.out.println("\n=== Appliance Menu ===");

            if (role.equals("customer")) {
                System.out.println("1. Add Appliance");
                System.out.println("2. View My Appliances");
                System.out.println("3. Update My Appliance");
                System.out.println("4. Delete My Appliance");
                System.out.println("5. Exit");
            } else if (role.equals("technician")) {
                System.out.println("1. View All Appliances");
                System.out.println("2. Exit");
            } else if (role.equals("admin")) {
            	System.out.println("1. Add Appliance");
                System.out.println("2. View My Appliances");
                System.out.println("3. View All Appliances");
                System.out.println("4. Update My Appliance");
                System.out.println("5. Update All Appliance");
                System.out.println("6. Delete My Appliance");
                System.out.println("7. Delete All Appliance");
            	System.out.println("8. Exit");
            } else {
                System.out.println("Unknown role. Exiting...");
                return;
            }
            System.out.print("Enter your choice: ");
            choice = getIntInput();
            
            if (role.equals("customer")) {
                switch (choice) {
                    case 1 -> addAppliance();
                    case 2 -> applianceService.viewAppliancesByOwner(currentUser.getUsername());
                    case 3 -> updateApplianceByOwner();
                    case 4 -> deleteApplianceByOwner();
                    case 5 -> System.out.println("Exiting Appliance Menu...");
                    default -> System.out.println("Invalid choice. Please enter a number 1-5.");
                }
            } else if (role.equals("technician")) {
                switch (choice) {
                    case 1 -> applianceService.viewAppliances();
                    case 2 -> System.out.println("Exiting Appliance Menu...");
                    default -> System.out.println("Invalid choice. Please enter a number 1-2.");
                }
            }else if (role.equals("admin")) {
            	switch (choice) {
                case 1 -> addAppliance();
                case 2 -> applianceService.viewAppliancesByOwner(currentUser.getUsername());
                case 3 -> applianceService.viewAppliances();
                case 4 -> updateApplianceByOwner();
                case 5 -> updateAppliance();
                case 6 -> deleteApplianceByOwner();
                case 7 -> deleteAppliance();
                case 8 -> System.out.println("Exiting Appliance Menu...");
                default -> System.out.println("Invalid choice. Please enter a number 1-8.");
                }
            }

            System.out.print("\nPress ENTER to continue...");
            scanner.nextLine();
        }while (!exitCondition(choice));
    }
    
    private boolean exitCondition(int choice) {
        return (role.equals("customer") && choice == 5) ||
               (role.equals("technician") && choice == 2) || 
               (role.equals("admin") && choice == 8);
    }

    private void addAppliance() {
        String id = getRequiredInput("Enter Appliance ID (e.g. AP001): ");
        String name = getRequiredInput("Enter Appliance Name: ");
        String type = getRequiredInput("Enter Appliance Type: ");
        String brand = getRequiredInput("Enter Brand: ");
        String model = getRequiredInput("Enter Model: ");
        String owner = currentUser.getUsername();

        Appliance appliance = new Appliance(id, name, type, brand, model, owner);
        applianceService.addAppliance(appliance);
    }
    
    /*
    private void addAppliance() {
        System.out.print("Enter Appliance ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Appliance Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Appliance Type: ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();
		String owner = currentUser.getUsername();

        Appliance appliance = new Appliance(id, name, type, brand, model, owner);
        applianceService.addAppliance(appliance);
    }*/

    private void updateApplianceByOwner() {
    	String id = getRequiredInput("Enter Appliance ID to update (e.g. AP001): ");
    	applianceService.updateApplianceByOwner(id, currentUser.getUsername(), scanner);
        /*System.out.print("Enter Appliance ID to update (e.g. AP001): ");
        String id = scanner.nextLine().trim();

        Appliance appliance = applianceService.findApplianceById(id);
        String owner = appliance.getOwnerUsername();
        if (appliance == null || !(owner.equals(currentUser))) {
            System.out.println("Error: Appliance not found or not owned by you.");
            return;
        }
        else
        	applianceService.updateApplianceByOwner(id, owner, scanner);*/
    }
    
    private void updateAppliance() {
    	String id = getRequiredInput("Enter Appliance ID to update (e.g. AP001): ");
        applianceService.updateAppliance(id, scanner);
        /*System.out.print("Enter Appliance ID to update (e.g. AP001): ");
        String id = scanner.nextLine().trim();

        Appliance appliance = applianceService.findApplianceById(id);
        if (appliance == null || !(appliance.getOwnerUsername().equals(currentUser))) {
            System.out.println("Error: Appliance not found or not owned by you.");
            return;
        }
        else
        	applianceService.updateAppliance(id, scanner);*/
        
    }
    
    private void deleteApplianceByOwner() {
    	String id = getRequiredInput("Enter Appliance ID to delete (e.g. AP001): ");
        applianceService.deleteApplianceByOwner(id, currentUser.getUsername());
        /*System.out.print("Enter Appliance ID to delete (e.g. AP001): ");
        String id = scanner.nextLine().trim();

        Appliance appliance = applianceService.findApplianceById(id);
        String owner = appliance.getOwnerUsername();
        if (appliance == null || !(owner.equals(currentUser))) {
            System.out.println("Error: Appliance not found or not owned by you.");
            return;
        }
        else
        	applianceService.deleteApplianceByOwner(id, owner);*/
    }

    private void deleteAppliance() {
    	String id = getRequiredInput("Enter Appliance ID to delete (e.g. AP001): ");
        applianceService.deleteAppliance(id);
        /*System.out.print("Enter Appliance ID to delete (e.g. AP001): ");
        String id = scanner.nextLine().trim();

        Appliance appliance = applianceService.findApplianceById(id);
        if (appliance == null || !(appliance.getOwnerUsername().equals(currentUser))) {
            System.out.println("Error: Appliance not found or not owned by you.");
            return;
        }
        else
        	applianceService.deleteAppliance(id);*/
    }

    // Helper
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private String getRequiredInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This field is required. Please enter a value.");
            }
        } while (input.isEmpty());
        return input;
    }
}