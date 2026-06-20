package Feature;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.*;

public class AppointmentService {
    private static final String FILE_NAME = "appointments.txt";
    private ApplianceService applianceService = new ApplianceService();
    private RepairSlotService repairSlotService = new RepairSlotService();

    // Book an appointment
    public void bookAppointment(String customerName, String slotId, String applianceId,
                                String issueDescription, String date, String time) {

        // Validate Appliance
        Appliance appliance = applianceService.findApplianceById(applianceId);
        if (appliance == null || !appliance.getOwnerUsername().equalsIgnoreCase(customerName)) {
            System.out.println("Appliance not found or not linked to this user. Please add your appliance first.");
            return;
        }

        // Validate Repair Slot
        List<RepairSlot> slots = repairSlotService.loadSlots();
        RepairSlot slot = repairSlotService.findSlotById(slots, slotId);
        if (slot == null) {
            System.out.println("Slot ID does not exist.");
            return;
        }
        if (slot.isBooked()) {
            System.out.println("Slot already booked. Please choose another.");
            return;
        }

        // Validate Date & Time format
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date/time format. Use yyyy-MM-dd and HH:mm.");
            return;
        }

        // Create Appointment
        Appointment appointment = Appointment.createNew(
                customerName, slotId, applianceId, issueDescription, date, time
        );

        List<Appointment> appointments = loadAppointments();
        appointments.add(appointment);
        saveAppointments(appointments);

        // Mark slot booked
        slot.setBooked(true);
        repairSlotService.updateSlot(slot);

        System.out.println("Appointment booked successfully!");
    }

    // Admin view all
    public void viewAppointments() {
        List<Appointment> appointments = loadAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appt : appointments) {
                System.out.println(appt);
            }
        }
    }

    // Customer view own (past & upcoming)
    public void viewCustomerAppointments(String username) {
        List<Appointment> appointments = loadAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> past = new ArrayList<>();
        List<Appointment> upcoming = new ArrayList<>();

        for (Appointment appt : appointments) {
            if (appt.getCustomerName().equalsIgnoreCase(username)) {
                LocalDate apptDate = LocalDate.parse(appt.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime apptTime = LocalTime.parse(appt.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

                if (apptDate.isBefore(today) || (apptDate.isEqual(today) && apptTime.isBefore(now))) {
                    past.add(appt);
                } else {
                    upcoming.add(appt);
                }
            }
        }

        System.out.println("\n--- Past Appointments ---");
        if (past.isEmpty()) System.out.println("No past appointments.");
        else past.forEach(System.out::println);

        System.out.println("\n--- Upcoming Appointments ---");
        if (upcoming.isEmpty()) System.out.println("No upcoming appointments.");
        else upcoming.forEach(System.out::println);
    }

    // Technician view own (past & upcoming)
    public void viewTechnicianAppointments(User currentName) {
        List<Appointment> appointments = loadAppointments();
        List<RepairSlot> slots = repairSlotService.loadSlots();
        if (appointments.isEmpty() || slots.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> past = new ArrayList<>();
        List<Appointment> upcoming = new ArrayList<>();

        for (Appointment appt : appointments) {
            RepairSlot slot = slots.stream()
                    .filter(s -> s.getSlotId().equalsIgnoreCase(appt.getSlotId()))
                    .findFirst()
                    .orElse(null);

            if (slot != null && slot.getTechnicianName().equalsIgnoreCase(currentName.getUsername())) {
                LocalDate apptDate = LocalDate.parse(appt.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime apptTime = LocalTime.parse(appt.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

                if (apptDate.isBefore(today) || (apptDate.isEqual(today) && apptTime.isBefore(now))) {
                    past.add(appt);
                } else {
                    upcoming.add(appt);
                }
            }
        }

        System.out.println("\n--- Technician Past Appointments ---");
        if (past.isEmpty()) System.out.println("No past appointments.");
        else past.forEach(System.out::println);

        System.out.println("\n--- Technician Upcoming Appointments ---");
        if (upcoming.isEmpty()) System.out.println("No upcoming appointments.");
        else upcoming.forEach(System.out::println);
    }

    // Reschedule
    public void rescheduleAppointment(int id, String newDate, String newTime) {
        List<Appointment> appointments = loadAppointments();
        for (Appointment appt : appointments) {
            if (appt.getAppointmentId() == id) {
                if (isValidDate(newDate) && isValidTime(newTime)) {
                    appt.setDate(newDate);
                    appt.setTime(newTime);
                    saveAppointments(appointments);
                    System.out.println("Appointment rescheduled!");
                } else {
                    System.out.println("Invalid date/time format.");
                }
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    // Cancel appointment & free slot
    public void cancelAppointment(int id) {
        List<Appointment> appointments = loadAppointments();
        Appointment target = null;

        for (Appointment appt : appointments) {
            if (appt.getAppointmentId() == id) {
                target = appt;
                break;
            }
        }

        if (target != null) {
            appointments.remove(target);
            saveAppointments(appointments);

            // free slot
            RepairSlot slot = repairSlotService.findSlotById(target.getSlotId());
            if (slot != null) {
                slot.setBooked(false);
                repairSlotService.updateSlot(slot);
            }

            System.out.println("Appointment cancelled & slot freed!");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // Validation helpers
    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Load appointments
    public List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return appointments;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                appointments.add(Appointment.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
        return appointments;
    }

    // Save appointments
    private void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Appointment a : appointments) {
                bw.write(a.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }
}