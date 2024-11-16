static void generateReports() {
    System.out.println("\n╭──────────────────────────────╮");
    System.out.println("│             ++ Customer Report ++              │");
    System.out.println("╰──────────────────────────────╯\n");

    customer.viewCustomers();

    int custId;
    do {
        System.out.print("\nEnter Customer ID for the report: ");
        while (!scan.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid Customer ID.");
            scan.next(); // clear the invalid input
        }
        custId = scan.nextInt();

        if (!conf.doesIDExist("customer", custId)) {
            System.out.println("Customer ID not found. Please try again.");
        }
    } while (!conf.doesIDExist("customer", custId));

    System.out.println("\n╭──────────────────────────────╮");
    System.out.println("│             ++ Customer Details ++              │");
    System.out.println("╰──────────────────────────────╯\n");
    System.out.printf("Customer ID  : %-15d%n", custId);
    System.out.printf("Name         : %-15s%n", conf.getDataFromID("customer", custId, "name"));
    System.out.printf("Email        : %-15s%n", conf.getDataFromID("customer", custId, "email"));
    System.out.println("────────────────────────────────────");

    if (conf.isTableEmpty("events WHERE customer_id = " + custId)) {
        System.out.println("No event records found for this customer.");
    } else {
        System.out.println("Event Details:");
        System.out.printf(" %-10s │ %-20s │ %-15s │ %-10s%n", "Event ID", "Venue", "Event Date", "Status");
        System.out.println("───────────────────────────────────────────────────────────────────────");

        String sql = "SELECT event.id, venue.id, event.event_date, event.status " +
                     "FROM event " +
                     "JOIN venue ON event.venue_id = venue.id " +
                     "WHERE event.customer_id = " + custId;

        String[] headers = {"   Event ID", "Venue", "Event Date", "Status"};
        String[] columns = {"id", "destination", "event_date", "status"};

        conf.viewRecords(sql, headers, columns);
        System.out.println("───────────────────────────────────────────────────────────────────────");
    }

    System.out.println("\n╭──────────────────────────────╮");
    System.out.println("│             ++ End of Details ++              │");
    System.out.println("╰──────────────────────────────╯\n");
}
