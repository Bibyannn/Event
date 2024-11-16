package eventtrckr;

import java.util.Scanner;

public class Venues {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void VenuesConfig() {
        int option;
        do {
            System.out.println("\n╔═════════════════════════╗");
            System.out.println("║                  Venue Menu              ║");
            System.out.println("╚═════════════════════════╝\n");
            System.out.println("1. Add Venue");
            System.out.println("2. View Venues");
            System.out.println("3. Edit Venue");
            System.out.println("4. Delete Venue");
            System.out.println("5. Check Venue Availability");
            System.out.println("6. Exit");
            
            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    addVenue();
                    break;
                case 2:
                    viewVenues();
                    break;
                case 3:
                    editVenue();
                    break;
                case 4:
                    deleteVenue();
                    break;
                case 5:
                    checkVenueAvailability();  // New option for checking venue availability
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 6);
    }

    private void addVenue() {
        System.out.println("Enter Venue Details:");
        System.out.print("\nVenue Name: ");
        String venuename = scan.nextLine();
        System.out.print("Capacity: ");
        String capacity = scan.nextLine();
        System.out.print("Location: ");
        String location = scan.nextLine();
        System.out.print("Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        
        // Assuming is_available is 1 for available venues
        System.out.print("Is the venue available? (1 for Yes, 0 for No): ");
        int isAvailable = scan.nextInt();
        scan.nextLine();

        String sql = "INSERT INTO venue (venuename, capacity, location, price, is_available) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, venuename, capacity, location, price, isAvailable);
    }

    public void viewVenues() {
        String query = "SELECT * FROM venue";
        String[] headers = {"ID", "venuename", "capacity", "location", "Price", "Availability"};
        String[] columns = {"id", "venuename", "capacity", "location", "Price", "is_available"};
        conf.viewRecords(query, headers, columns);
    }

    private void editVenue() {
        System.out.print("Enter Venue ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("venue", id)) {
            System.out.println("Venue ID not found.");
            return;
        }

        System.out.println("Enter New Venue Details:");
        System.out.print("New Venue Name: ");
        String venuename = scan.nextLine();
        System.out.print("New Capacity: ");
        String capacity = scan.nextLine();
        System.out.print("New Location: ");
        String location = scan.nextLine();
        System.out.print("New Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        
        System.out.print("Is the venue available? (1 for Yes, 0 for No): ");
        int isAvailable = scan.nextInt();
        scan.nextLine();

        String sql = "UPDATE venue SET venuename = ?, capacity = ?, location = ?, price = ?, is_available = ? WHERE id = ?";
        conf.updateRecord(sql, venuename, capacity, location, price, isAvailable, id);
    }

    private void deleteVenue() {
        System.out.print("Enter Venue ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM venue WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    private void checkVenueAvailability() {
        System.out.print("Enter Venue ID to check availability: ");
        int id = scan.nextInt();
        scan.nextLine();

        String query = "SELECT is_available FROM venue WHERE id = ?";
        int availability = conf.getRecordValue(query, id); // Assuming conf.getRecordValue returns the availability value (0 or 1)

        if (availability == 1) {
            System.out.println("The venue is available.");
        } else if (availability == 0) {
            System.out.println("The venue is unavailable.");
        } else {
            System.out.println("Venue ID not found or invalid.");
        }
    }
}
