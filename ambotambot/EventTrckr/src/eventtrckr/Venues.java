
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
            System.out.println("5. Exit");
            
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
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addVenue() {
        System.out.println("Enter Venue Details:");
        System.out.print("\nVenue Name: ");
        String venuename = scan.nextLine();
        System.out.print("Capacity: ");
        String capacity = scan.nextLine();
        System.out.print("location: ");
        String location = scan.nextLine();
        System.out.print("Price: ");
        double price = scan.nextDouble();
        scan.nextLine();


        String sql = "UPDATE venue SET venuename = ?, capacity = ?, location = ?, price = ?";
        conf.updateRecord(sql, venuename, capacity, location, price);
    }

    public void viewVenues() {
        String query = "SELECT * FROM venue";
        String[] headers = {"ID", "venuename", "capacity", "location", "Price"};
        String[] columns = {"id", "venuename", "capacity", "location", "Price"};
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


        String sql =  "UPDATE venue SET venuename = ?, capacity = ?, location = ?, price = ? WHERE id = ?";
        conf.updateRecord(sql, venuename, capacity, location, price, id);
    }

    private void deleteVenue() {
        System.out.print("Enter Venue ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM venue WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
