import java.util.ArrayList;
import java.util.Scanner;

class TravelItineraryPlanner {
    private ArrayList<String> destinations;
    private ArrayList<String> startDates;
    private ArrayList<String> endDates;
    private ArrayList<String> preferences;
    private ArrayList<Double> budgets;
    private ArrayList<String> weatherConditions;
    private String emergencyContact;

    public TravelItineraryPlanner() {
        destinations = new ArrayList<>();
        startDates = new ArrayList<>();
        endDates = new ArrayList<>();
        preferences = new ArrayList<>();
        budgets = new ArrayList<>();
        weatherConditions = new ArrayList<>();
    }

    // 1. Add Destination, Date, Preference, Budget
    public void addDestination(String destination, String startDate, String endDate, String preference, double budget, String weather) {
        destinations.add(destination);
        startDates.add(startDate);
        endDates.add(endDate);
        preferences.add(preference);
        budgets.add(budget);
        weatherConditions.add(weather);
    }

    // 2. Generate Itinerary
    public void generateItinerary() {
        System.out.println("\nTravel Itinerary:");
        double totalBudget = 0;
        for (int i = 0; i < destinations.size(); i++) {
            System.out.println("Destination: " + destinations.get(i));
            System.out.println("Start Date: " + startDates.get(i));
            System.out.println("End Date: " + endDates.get(i));
            System.out.println("Preference: " + preferences.get(i));
            System.out.println("Budget: $" + budgets.get(i));
            System.out.println("Weather Forecast: " + weatherConditions.get(i));
            System.out.println("----------------------------");
            totalBudget += budgets.get(i);
        }
        System.out.println("Total Estimated Budget: $" + totalBudget);
    }

    // 6. Travel Checklist
    public void showChecklist() {
        System.out.println("\nChecklist of Essentials:");
        System.out.println("1. Passport");
        System.out.println("2. Flight Tickets");
        System.out.println("3. Hotel Bookings");
        System.out.println("4. Medicines");
        System.out.println("5. Local Currency");
        System.out.println("6. Emergency Contacts");
        System.out.println("----------------------------");
    }

    // 7. Emergency Contacts
    public void addEmergencyContact(String contact) {
        emergencyContact = contact;
    }

    public void showEmergencyContact() {
        System.out.println("\nEmergency Contact: " + emergencyContact);
    }

    // 3. Simulated Map Integration
    public void showMap(String destination) {
        System.out.println("\nShowing map for " + destination + "...");
        System.out.println("[Map Simulation]");
        System.out.println("----------------------------");
    }

    // 4. Simulated Weather API Integration
    public String getWeatherForecast(String destination) {
        // In reality, this would call a weather API. Here it's simulated.
        return "Sunny with light showers";
    }

    public static void main(String[] args) {
        TravelItineraryPlanner planner = new TravelItineraryPlanner();
        Scanner scanner = new Scanner(System.in);
        String input;

        // Add Emergency Contact
        System.out.println("Enter Emergency Contact:");
        String emergencyContact = scanner.nextLine();
        planner.addEmergencyContact(emergencyContact);

        do {
            // 1. Input for Destination, Dates, Preferences, Budget
            System.out.println("\nEnter Destination:");
            String destination = scanner.nextLine();

            System.out.println("Enter Start Date (YYYY-MM-DD):");
            String startDate = scanner.nextLine();

            System.out.println("Enter End Date (YYYY-MM-DD):");
            String endDate = scanner.nextLine();

            System.out.println("Enter Preferences (e.g., sightseeing, relaxation):");
            String preference = scanner.nextLine();

            System.out.println("Enter Budget for this destination:");
            double budget = Double.parseDouble(scanner.nextLine());

            // 4. Simulated Weather Information
            String weather = planner.getWeatherForecast(destination);

            planner.addDestination(destination, startDate, endDate, preference, budget, weather);

            System.out.println("Do you want to add another destination? (yes/no)");
            input = scanner.nextLine();
        } while (input.equalsIgnoreCase("yes"));

        // 2. Generate Itinerary
        planner.generateItinerary();

        // 3. Show Map for each destination
        for (String destination : planner.destinations) {
            planner.showMap(destination);
        }

        // 6. Show Checklist
        planner.showChecklist();

        // 7. Show Emergency Contact
        planner.showEmergencyContact();
    }
}