package Feature;

import java.util.Scanner;

public class AppointmentMenu {
    private AppointmentService manager = new AppointmentService();
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private String role;

    public AppointmentMenu(User currentUser) {
        this.currentUser = currentUser;
        this.role = currentUser.getRole().toLowerCase();
    }

    public void showMenu() {
        int choice;

        do {
            System.out.println("\n=== Repair Appointment Menu ===");
            if (role.equals("customer")) {
                System.out.println("1. Book Repair Appointment");
                System.out.println("2. View My Appointments");
                System.out.println("3. Reschedule Appointment");
                System.out.println("4. Cancel Appointment");
                System.out.println("5. Exit");
            } else if (role.equals("technician")) {
                System.out.println("1. View Assigned Appointments");
                System.out.println("2. View All Appointments");
                System.out.println("3. Exit");
            } else if (role.equals("admin")) {
            	System.out.println("1. Book Repair Appointment");
                System.out.println("2. View My Appointments");
                System.out.println("3. View All Appointments");
                System.out.println("4. Reschedule Appointment");
                System.out.println("5. Cancel Appointment");
                System.out.println("6. Exit");
            } else {
                System.out.println("Unknown role. Exiting...");
                return;
            }

            System.out.print("Enter your choice: ");
            choice = getIntInput();

            if (role.equals("customer")) {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Slot ID (e.g. RS001): ");
                        String slot = scanner.nextLine().trim();
                        System.out.print("Enter Appliance ID (e.g. AP001): ");
                        String applianceId = scanner.nextLine().trim();
                        System.out.print("Enter Issue Description: ");
                        String issue = scanner.nextLine().trim();
                        System.out.print("Enter Date (yyyy-MM-dd): ");
                        String date = scanner.nextLine().trim();
                        System.out.print("Enter Time (HH:mm): ");
                        String time = scanner.nextLine().trim();
                        manager.bookAppointment(currentUser.getUsername(), slot, applianceId, issue, date, time);
                    }
                    case 2 -> manager.viewCustomerAppointments(currentUser.getUsername());
                    case 3 -> {
                        System.out.print("Enter Appointment ID to reschedule (e.g. 1): ");
                        int id = getIntInput();
                        System.out.print("Enter new Date (yyyy-MM-dd): ");
                        String newDate = scanner.nextLine().trim();
                        System.out.print("Enter new Time (HH:mm): ");
                        String newTime = scanner.nextLine().trim();
                        manager.rescheduleAppointment(id, newDate, newTime);
                    }
                    case 4 -> {
                        System.out.print("Enter Appointment ID to cancel (e.g. 1): ");
                        int id = getIntInput();
                        System.out.print("Are you sure you want to cancel appointment " + id + "? (y/n): ");
                        String confirm = scanner.nextLine().trim().toLowerCase();
                        if (confirm.equals("y")) {
                            manager.cancelAppointment(id);
                        } else {
                            System.out.println("Cancel operation aborted.");
                        }
                    }
                    case 5 -> System.out.println("Exiting Appointment Menu...");
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } else if (role.equals("technician")) {
                switch (choice) {
                    case 1 -> manager.viewTechnicianAppointments(currentUser);
                    case 2 -> manager.viewAppointments();
                    case 3 -> System.out.println("Exiting Appointment Menu...");
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } else if (role.equals("admin")) {
                switch (choice) {
                    case 1 -> {
                    System.out.print("Enter Slot ID (e.g. RS001): ");
                    String slot = scanner.nextLine().trim();
                    System.out.print("Enter Appliance ID (e.g. AP001): ");
                    String applianceId = scanner.nextLine().trim();
                    System.out.print("Enter Issue Description: ");
                    String issue = scanner.nextLine().trim();
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    String date = scanner.nextLine().trim();
                    System.out.print("Enter Time (HH:mm): ");
                    String time = scanner.nextLine().trim();
                    manager.bookAppointment(currentUser.getUsername(), slot, applianceId, issue, date, time);
                }
                case 2 -> manager.viewCustomerAppointments(currentUser.getUsername());
                case 3 -> manager.viewAppointments();
                case 4 -> {
                    System.out.print("Enter Appointment ID to reschedule (e.g. 1): ");
                    int id = getIntInput();
                    System.out.print("Enter new Date (yyyy-MM-dd): ");
                    String newDate = scanner.nextLine().trim();
                    System.out.print("Enter new Time (HH:mm): ");
                    String newTime = scanner.nextLine().trim();
                    manager.rescheduleAppointment(id, newDate, newTime);
                }
                case 5 -> {
                    System.out.print("Enter Appointment ID to cancel (e.g. 1): ");
                    int id = getIntInput();
                    System.out.print("Are you sure you want to cancel appointment " + id + "? (y/n): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("y")) {
                        manager.cancelAppointment(id);
                    } else {
                        System.out.println("Cancel operation aborted.");
                    }
                }
                case 6 -> System.out.println("Exiting Appointment Menu...");
                default -> System.out.println("Invalid choice! Try again.");
                }
            }

        } while (!exitCondition(choice));
    }

    private boolean exitCondition(int choice) {
        return (role.equals("customer") && choice == 5) ||
               (role.equals("technician") && choice == 3) ||
               (role.equals("admin") && choice == 6);
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}