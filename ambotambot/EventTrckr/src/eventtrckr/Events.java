
package eventtrckr;

import java.util.Scanner;

public class Events {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void eventsConfig() {
        int option;
        do {
            System.out.println("\n╔═════════════════════════╗");
            System.out.println("║               Event Menu              ║");
            System.out.println("╚═════════════════════════╝\n");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Exit");
            
            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    addEvent();
                    break;
                case 2:
                    viewEvents();
                    break;
                case 3:
                    editEvent();
                    break;
                case 4:
                    deleteEvent();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addEvent() {
        System.out.println("Enter Event Details:");

        int customerId;
        do {
            System.out.print("\nCustomer ID: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customer", customerId)) {
                System.out.println("Customer ID doesn't exist.");
            }
        } while (!conf.doesIDExist("customer", customerId));

        int venueId;
        do {
            System.out.print("Venue ID: ");
            venueId = scan.nextInt();
            if (!conf.doesIDExist("venue", venueId)) {
                System.out.println("Venue ID doesn't exist.");
            }
        } while (!conf.doesIDExist("venue", venueId));

        scan.nextLine(); 

        System.out.print("Event Date (YYYY-MM-DD): ");
        String eventDate = scan.nextLine();
        System.out.print("Status(Rescheduled/Cancelled): ");
        String status = scan.nextLine();

        String sql = "INSERT INTO events (customer_id, venue_id, event_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, customerId, venueId, eventDate, status);
    }

    public void viewEvents() {
        String query = "SELECT * FROM events";
        String[] headers = {"ID", "Customer ID", "Venue ID", "Event Date", "Status"};
        String[] columns = {"id", "customer_id", "venue_id", "event_date", "status"};
        conf.viewRecords(query, headers, columns);
    }

    private void editEvent() {
        System.out.print("Enter Event ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("event", id)) {
            System.out.println("Event ID not found.");
            return;
        }

        System.out.println("Enter New Event Details:");
        System.out.print("New Customer ID: ");
        int customerId = scan.nextInt();
        System.out.print("New Venue ID: ");
        int venueId = scan.nextInt();
        scan.nextLine();
        System.out.print("New Event Date (YYYY-MM-DD): ");
        String eventDate = scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE events SET customer_id = ?, venue_id = ?, event_date = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, customerId, venueId, eventDate, status, id);
    }

    private void deleteEvent() {
        System.out.print("Enter Event ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM events WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
