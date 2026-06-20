package Feature;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * RepairSlot class representing a time slot for repairs
 */
class RepairSlot{
    private String slotId;
    private String technicianName;
    private String day;
    private LocalDate date;
    private LocalTime time;
    private boolean isBooked;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public RepairSlot(String slotId, String technicianName, String day, LocalDate date, LocalTime time) {
        this.slotId = slotId;
        this.technicianName = technicianName;
        this.day = day;
        this.date = date;
        this.time = time;
        this.isBooked = false;
    }

    // Getters and Setters
    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }
    
    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }

    @Override
    public String toString() {
        return String.format("Slot ID: %s | Technician: %s | Day: %s | Date: %s | Time: %s | Status: %s",
                slotId, technicianName, day, date, time, isBooked ? "Booked" : "Available");
    }
    
    // Convert to CSV format
    public String toCSV() {
        return slotId + "," + technicianName + "," + day + "," +
               date.format(DATE_FORMATTER) + "," + time.format(TIME_FORMATTER) + "," + isBooked;
    }

    // Create RepairSlot from CSV line
    public static RepairSlot fromCSV(String line) {
        String[] parts = line.split(",", -1);
        RepairSlot slot = new RepairSlot(
                parts[0],                                     // slotId
                parts[1],                                     // technicianName
                parts[2],                                     // day
                LocalDate.parse(parts[3], DATE_FORMATTER),    // date
                LocalTime.parse(parts[4], TIME_FORMATTER)     // time
        );
        slot.setBooked(Boolean.parseBoolean(parts[5]));       // isBooked
        return slot;
    }
}

