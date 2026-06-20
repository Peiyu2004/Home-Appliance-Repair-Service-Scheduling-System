package Feature;

import java.util.Scanner;

public class RepairSlotMenu {
    private RepairSlotService slotService;
    private Scanner scanner;
    private User currentUser;

    public RepairSlotMenu(User currentUser) {
        this.slotService = new RepairSlotService();
        this.scanner = new Scanner(System.in);
        this.currentUser = currentUser;
    }

    public void showMenu() {
        boolean running = true;
        int choice;
        while (running) {
            System.out.println("\n=== Repair Slot Menu ===");

            switch (currentUser.getRole().toLowerCase()) {
                case "customer" -> {
                    System.out.println("1. View All Slots");
                    System.out.println("2. Search Slots");
                    System.out.println("3. Exit to Main Menu");
                    System.out.print("Enter choice: ");

                    choice = getIntInput();
                    switch (choice) {
                        case 1 -> slotService.viewRepairSlots();
                        case 2 -> slotService.searchRepairSlots(scanner);
                        case 3 -> running = false;
                        default -> System.out.println("Invalid choice.");
                    }
                }

                case "technician" -> {
                    System.out.println("1. Add Slot");
                    System.out.println("2. View My Slots");
                    System.out.println("3. View All Slots");
                    System.out.println("4. Update Slot");
                    System.out.println("5. Delete Slot");
                    System.out.println("6. Search Slots");
                    System.out.println("7. Exit to Main Menu");
                    System.out.print("Enter choice: ");

                    choice = getIntInput();
                    switch (choice) {
                        case 1 -> slotService.addRepairSlot(scanner, currentUser.getUsername());
                        case 2 -> slotService.viewTechnicianSlots(currentUser.getUsername());
                        case 3 -> slotService.viewRepairSlots();
                        case 4 -> slotService.updateRepairSlot(scanner);
                        case 5 -> slotService.deleteRepairSlot(scanner);
                        case 6 -> slotService.searchRepairSlots(scanner);
                        case 7 -> running = false;
                        default -> System.out.println("Invalid choice.");
                    }
                }

                case "admin" -> {
                	    System.out.println("1. Add Slot");
                        System.out.println("2. View Your Slots");
                        System.out.println("3. View All Slots");
                        System.out.println("4. Update Slot");
                        System.out.println("5. Delete Slot");
                        System.out.println("6. Search Slots");
                        System.out.println("7. Exit to Main Menu");
                        System.out.print("Enter choice: ");

                        choice = getIntInput();
                        switch (choice) {
                        case 1 -> slotService.addRepairSlot(scanner, currentUser.getUsername());
                        case 2 -> slotService.viewTechnicianSlots(currentUser.getUsername());
                        case 3 -> slotService.viewRepairSlots();
                        case 4 -> slotService.updateRepairSlot(scanner);
                        case 5 -> slotService.deleteRepairSlot(scanner);
                        case 6 -> slotService.searchRepairSlots(scanner);
                        case 7 -> running = false;
                        default -> System.out.println("Invalid choice.");
                        }
                    }
               default -> {
                    System.out.println("Unknown role. Returning to main menu...");
                    running = false;
                }
            }
        }
    }

    // Helper for safe int input
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}