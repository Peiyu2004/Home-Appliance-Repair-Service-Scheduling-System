package Feature;

public class Appointment {
    private static int idCounter = 1;
    private int appointmentId;
    private String customerName;
    private String slotId;
    private String applianceId;  // changed to applianceId (was applianceType before)
    private String issueDescription;
    private String date; // store as String for file, but parsed for validation
    private String time;

    // Constructor for loading from file
    public Appointment(int appointmentId, String customerName, String slotId, String applianceId,
                       String issueDescription, String date, String time) {
        this.appointmentId = appointmentId;
        this.customerName = customerName;
        this.slotId = slotId;
        this.applianceId = applianceId;
        this.issueDescription = issueDescription;
        this.date = date;
        this.time = time;

        // maintain counter
        if (appointmentId >= idCounter) {
            idCounter = appointmentId + 1;
        }
    }

    // Factory for NEW appointments
    public static Appointment createNew(String customerName, String slotId, String applianceId,
                                        String issueDescription, String date, String time) {
        return new Appointment(idCounter++, customerName, slotId, applianceId, issueDescription, date, time);
    }

    // Convert to CSV
    public String toCSV() {
        return appointmentId + "," + customerName + "," + slotId + "," + applianceId + "," +
                issueDescription + "," + date + "," + time;
    }

    // Parse from CSV
    public static Appointment fromCSV(String line) {
        String[] parts = line.split(",", -1);
        return new Appointment(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6]
        );
    }

    // Getters & setters
    public int getAppointmentId() 
    { 
    	return appointmentId; 
    }
    public String getCustomerName() 
    { 
    	return customerName; 
    }
    public String getSlotId() 
    { 
    	return slotId; 
    }
    public String getApplianceId() 
    { 
    	return applianceId; 
    }
    public String getDate() 
    { 
    	return date; 
    }
    public void setDate(String date) 
    { 
    	this.date = date; 
    }
    public String getTime() 
    { 
    	return time; 
    }
    public void setTime(String time) 
    { 
    	this.time = time; 
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
                " | Customer: " + customerName +
                " | Slot ID: " + slotId +
                " | Appliance ID: " + applianceId +
                " | Issue: " + issueDescription +
                " | Date: " + date +
                " | Time: " + time;
    }
}