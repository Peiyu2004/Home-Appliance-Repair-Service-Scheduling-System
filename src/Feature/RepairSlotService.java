package Feature;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Service class for managing RepairSlot CRUD operations + Search
 */
public class RepairSlotService {
    private static final String SLOT_FILE = "repair_slots.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // File I/O
    public List<RepairSlot> loadSlots() {
        List<RepairSlot> slots = new ArrayList<>();
        File file = new File(SLOT_FILE);
        if (!file.exists()) return slots;

        try (BufferedReader reader = new BufferedReader(new FileReader(SLOT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RepairSlot slot = RepairSlot.fromCSV(line);
                if (slot != null) slots.add(slot);
            }
        } catch (Exception e) {
            System.out.println("Error loading slots: " + e.getMessage());
        }
        return slots;
    }

    public void saveSlots(List<RepairSlot> slots) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SLOT_FILE))) {
            for (RepairSlot slot : slots) {
                writer.write(slot.toCSV());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving slots: " + e.getMessage());
        }
    }

    // CRUD
    public void addRepairSlot(Scanner scanner, String technicianName) {
        try {
            System.out.println("\n=== Add Repair Slot ===");
            String slotId = getRequiredInput(scanner, "Enter Slot ID (e.g. RS001): ");
            /*System.out.print("Enter Slot ID (e.g. RS001): ");
            String slotId = scanner.nextLine().trim(); */

            List<RepairSlot> slots = loadSlots();
            if (findSlotById(slots, slotId) != null) {
                System.out.println("Error: Slot ID already exists!");
                return;
            }

            String day = getRequiredInput(scanner, "Enter Day (e.g., Monday): ");
            /*System.out.print("Enter Day (e.g., Monday): ");
            String day = scanner.nextLine().trim();*/

            System.out.print("Enter Date (YYYY-MM-DD): ");
            LocalDate date;
            try {
                date = LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
                return;
            }

            System.out.print("Enter Time (HH:MM): ");
            LocalTime time;
            try {
                time = LocalTime.parse(scanner.nextLine().trim(), TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format.");
                return;
            }

            slots.add(new RepairSlot(slotId, technicianName, day, date, time));
            saveSlots(slots);
            System.out.println("Repair slot added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding slot: " + e.getMessage());
        }
    }

    public void viewRepairSlots() {
        List<RepairSlot> slots = loadSlots();
        if (slots.isEmpty()) {
            System.out.println("No repair slots available.");
            return;
        }
        System.out.println("\n=== All Repair Slots ===");
        for (RepairSlot slot : slots) {
            System.out.println(slot);
        }
    }
    
    public void viewTechnicianSlots(String technicianName) {
        List<RepairSlot> slots = loadSlots();
        boolean found = false;
        System.out.println("\n=== Your Repair Slots ===");
        for (RepairSlot slot : slots) {
            if (slot.getTechnicianName().equalsIgnoreCase(technicianName)) {
                System.out.println(slot);
                found = true;
            }
        }
        if (!found) System.out.println("No slots assigned to you.");
    }

    public void updateRepairSlot(Scanner scanner) {
    	String slotId = getRequiredInput(scanner, "Enter Slot ID (e.g. RS001): ");
        /*System.out.print("Enter Slot ID to update (e.g. RS001): ");
        String slotId = scanner.nextLine().trim();*/

        List<RepairSlot> slots = loadSlots();
        RepairSlot slot = findSlotById(slots, slotId);

        if (slot == null) {
            System.out.println("Slot not found.");
            return;
        }
        if (slot.isBooked()) {
            System.out.println("Cannot update a booked slot.");
            return;
        }

        System.out.print("New Day (" + slot.getDay() + "): ");
        String day = scanner.nextLine().trim();
        if (!day.isEmpty()) slot.setDay(day);

        System.out.print("New Date (" + slot.getDate() + "): ");
        String d = scanner.nextLine().trim();
        if (!d.isEmpty()) {
            try { slot.setDate(LocalDate.parse(d, DATE_FORMATTER)); }
            catch (DateTimeParseException e) { System.out.println("Invalid date."); }
        }

        System.out.print("New Time (" + slot.getTime() + "): ");
        String t = scanner.nextLine().trim();
        if (!t.isEmpty()) {
            try { slot.setTime(LocalTime.parse(t, TIME_FORMATTER)); }
            catch (DateTimeParseException e) { System.out.println("Invalid time."); }
        }

        saveSlots(slots);
        System.out.println("Slot updated successfully!");
    }

    public void deleteRepairSlot(Scanner scanner) {
    	String slotId = getRequiredInput(scanner, "Enter Slot ID (e.g. RS001): ");
        /*System.out.print("Enter Slot ID to delete (e.g. RS001): ");
        String slotId = scanner.nextLine().trim();*/

        List<RepairSlot> slots = loadSlots();
        RepairSlot slot = findSlotById(slots, slotId);

        if (slot == null) {
            System.out.println("Slot not found.");
            return;
        }
        if (slot.isBooked()) {
            System.out.println("Cannot delete a booked slot.");
            return;
        }

        slots.remove(slot);
        saveSlots(slots);
        System.out.println("Slot deleted successfully!");
    }

    // Search
    public void searchRepairSlots(Scanner scanner) {
        System.out.println("\n=== Search Repair Slots ===");
        System.out.print("Enter Technician Name (or press Enter to skip): ");
        String tech = scanner.nextLine().trim();

        System.out.print("Enter Date (YYYY-MM-DD) or press Enter to skip: ");
        String dateStr = scanner.nextLine().trim();

        System.out.print("Enter Time (HH:MM) or press Enter to skip: ");
        String timeStr = scanner.nextLine().trim();

        LocalDate searchDate = null;
        LocalTime searchTime = null;
        try {
            if (!dateStr.isEmpty()) searchDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            if (!timeStr.isEmpty()) searchTime = LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date or time format.");
            return;
        }

        List<RepairSlot> slots = loadSlots();
        List<RepairSlot> results = new ArrayList<>();

        for (RepairSlot slot : slots) {
            boolean match = !slot.isBooked();
            if (!tech.isEmpty() && !slot.getTechnicianName().equalsIgnoreCase(tech)) match = false;
            if (searchDate != null && !slot.getDate().equals(searchDate)) match = false;
            if (searchTime != null && !slot.getTime().equals(searchTime)) match = false;

            if (match) results.add(slot);
        }

        if (results.isEmpty()) {
            System.out.println("No available slots found.");
        } else {
            System.out.println("\nAvailable Repair Slots:");
            for (RepairSlot s : results) {
                System.out.println(s);
            }
        }
    }

    // Helpers
    public RepairSlot findSlotById(List<RepairSlot> slots, String slotId) {
        for (RepairSlot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(slotId)) {
                return slot;
            }
        }
        return null;
    }

    // Overload (auto-loads slots)
    public RepairSlot findSlotById(String slotId) {
        List<RepairSlot> slots = loadSlots();
        return findSlotById(slots, slotId);
    }
    
    // Mandatory input
    private String getRequiredInput(Scanner scanner, String prompt) {
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

    // Update a slot (used when booking)
    public void updateSlot(RepairSlot updatedSlot) {
        List<RepairSlot> slots = loadSlots();
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getSlotId().equals(updatedSlot.getSlotId())) {
                slots.set(i, updatedSlot);
                break;
            }
        }
        saveSlots(slots);
    }
}