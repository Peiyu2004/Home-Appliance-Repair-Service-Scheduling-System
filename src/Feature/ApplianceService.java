package Feature;

import java.io.*;
import java.util.*;

public class ApplianceService {
    private static final String FILE_NAME = "appliances.txt";

    // Add new appliance
    public void addAppliance(Appliance appliance) {
    	List<Appliance> appliances = loadAppliances();
        for (Appliance a : appliances) {
            if (a.getApplianceId().equalsIgnoreCase(appliance.getApplianceId())) {
                System.out.println("Appliance ID already exists!");
                return;
            }
        }
        appliances.add(appliance);
        saveAppliances(appliances);
        System.out.println("Appliance added successfully! Please proceed and view your appointment ID.");
    }

    // View all appliances
    public void viewAppliances() {
        List<Appliance> appliances = loadAppliances();
        if (appliances.isEmpty()) {
            System.out.println("No appliances found.");
        } else {
            System.out.println("\n=== Registered Appliances ===");
            for (Appliance a : appliances) {
                System.out.println(a);
            }
        }
    }

    // View appliances owned by a specific user
    public void viewAppliancesByOwner(String ownerUsername) {
        List<Appliance> appliances = loadAppliances();
        boolean found = false;

        System.out.println("\n=== Your Appliances ===");
        for (Appliance a : appliances) {
            if (a.getOwnerUsername().equalsIgnoreCase(ownerUsername)) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("You have not registered any appliances yet.");
        }
    }
    
    /*
    // Get appliance by owner (to check if already exists)
    public Appliance getApplianceByOwner(String ownerUsername) {
        List<Appliance> appliances = loadAppliances();
        for (Appliance a : appliances) {
            if (a.getOwnerUsername().equalsIgnoreCase(ownerUsername)) {
                return a;
            }
        }
        return null; // not found
    }*/

    // Find appliance by ID
    public Appliance findApplianceById(String applianceId) {
        List<Appliance> appliances = loadAppliances();
        for (Appliance a : appliances) {
            if (a.getApplianceId().equalsIgnoreCase(applianceId)) {
                return a;
            }
        }
        return null;
    }

    // Update only if the owner matches
    public void updateApplianceByOwner(String applianceId, String ownerUsername, Scanner scanner) {
        List<Appliance> appliances = loadAppliances();
        for (Appliance a : appliances) {
            if (a.getApplianceId().equals(applianceId) && a.getOwnerUsername().equalsIgnoreCase(ownerUsername)) {
            	updateDetails(scanner, a);
                saveAppliances(appliances);
                System.out.println("Appliance updated successfully!");
                return;
            }
        }
        System.out.println("Appliance not found or you are not the owner.");
    }

    // Update appliance
    public void updateAppliance(String applianceId, Scanner scanner) {
        List<Appliance> appliances = loadAppliances();
        for (Appliance a : appliances) {
            if (a.getApplianceId().equals(applianceId)) {
                updateDetails(scanner, a);
                saveAppliances(appliances);
                System.out.println("Appliance updated successfully!");
                return;
            }
        }
        System.out.println("Appliance ID not found.");
    }
    
    private void updateDetails(Scanner scanner, Appliance a) {
        System.out.println("Current details: " + a);

        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) a.setName(name);

        System.out.print("Enter new type (or press Enter to keep current): ");
        String type = scanner.nextLine().trim();
        if (!type.isEmpty()) a.setType(type);

        System.out.print("Enter new brand (or press Enter to keep current): ");
        String brand = scanner.nextLine().trim();
        if (!brand.isEmpty()) a.setBrand(brand);

        System.out.print("Enter new model (or press Enter to keep current): ");
        String model = scanner.nextLine().trim();
        if (!model.isEmpty()) a.setModel(model);
    }
    
    // Delete appliance (restricted by owner)
    public void deleteApplianceByOwner(String applianceId, String ownerUsername) {
        List<Appliance> appliances = loadAppliances();
        boolean removed = appliances.removeIf(a -> 
            a.getApplianceId().equalsIgnoreCase(applianceId)
            && a.getOwnerUsername().equalsIgnoreCase(ownerUsername)
        );
        /*
        boolean removed = false;
    
        Iterator<Appliance> iterator = appliances.iterator();
        while (iterator.hasNext()) {
            Appliance a = iterator.next();
            if (a.getApplianceId().equalsIgnoreCase(applianceId)
                    && a.getOwnerUsername().equalsIgnoreCase(ownerUsername)) {
                iterator.remove();
                removed = true;
                break;
            }
        }*/
        if (removed) {
            saveAppliances(appliances);
            System.out.println("Appliance deleted successfully!");
        } else {
            System.out.println("Appliance not found or you are not the owner.");
        }
    }

    // Delete appliance
    public void deleteAppliance(String applianceId) {
        List<Appliance> appliances = loadAppliances();
        boolean removed = appliances.removeIf(a -> a.getApplianceId().equals(applianceId));
        if (removed) {
            saveAppliances(appliances);
            System.out.println("Appliance deleted successfully!");
        } else {
            System.out.println("Appliance ID not found.");
        }
    }
    
    // Load appliances from text file
    private List<Appliance> loadAppliances() {
        List<Appliance> appliances = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return appliances;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    appliances.add(new Appliance(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading appliances: " + e.getMessage());
        }
        return appliances;
    }

    // Save appliances to text file
    private void saveAppliances(List<Appliance> appliances) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Appliance a : appliances) {
                bw.write(String.join(",",
                        a.getApplianceId(),
                        a.getName(),
                        a.getType(),
                        a.getBrand(),
                        a.getModel(),
                        a.getOwnerUsername()
                ));
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving appliances: " + e.getMessage());
        }
    }
}