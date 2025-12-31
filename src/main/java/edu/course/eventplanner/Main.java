package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GuestListManager guestListManager = new GuestListManager();
    private static double budget = 0.0;

    public static void main(String[] args) {
        System.out.println("=== Event Planner Mini ===");
        System.out.println();

        // Get initial budget
        getBudgetFromUser();

        // Get initial number of guests
        getInitialGuests();

        // Main menu loop
        boolean running = true;
        while (running) {
            running = showMainMenu();
        }

        System.out.println("\nThank you for using Event Planner Mini!");
        scanner.close();
    }

    private static void getBudgetFromUser() {
        while (true) {
            System.out.print("Enter your event budget: $");
            try {
                String input = scanner.nextLine().trim();
                budget = Double.parseDouble(input);
                if (budget > 0) {
                    System.out.println("Budget set to: $" + String.format("%.2f", budget));
                    System.out.println();
                    break;
                } else {
                    System.out.println("Please enter a positive amount.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void getInitialGuests() {
        while (true) {
            System.out.print("How many guests would you like to add initially? ");
            try {
                String input = scanner.nextLine().trim();
                int numberOfGuests = Integer.parseInt(input);
                if (numberOfGuests < 0) {
                    System.out.println("Please enter a non-negative number.");
                    continue;
                }

                for (int i = 0; i < numberOfGuests; i++) {
                    System.out.println("\nGuest " + (i + 1) + ":");
                    addGuestInteractive();
                }

                System.out.println("\n" + guestListManager.getGuestCount() + " guest(s) added successfully.");
                System.out.println();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static boolean showMainMenu() {
        System.out.println("========== MAIN MENU ==========");
        System.out.println("Budget: $" + String.format("%.2f", budget));
        System.out.println("Total Guests: " + guestListManager.getGuestCount());
        System.out.println("===============================");
        System.out.println("1. Add Guest");
        System.out.println("2. Remove Guest");
        System.out.println("3. View All Guests");
        System.out.println("4. Find Guest");
        System.out.println("5. Update Budget");
        System.out.println("6. Exit");
        System.out.println("===============================");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine().trim();
        System.out.println();

        switch (choice) {
            case "1":
                addGuestInteractive();
                break;
            case "2":
                removeGuestInteractive();
                break;
            case "3":
                viewAllGuests();
                break;
            case "4":
                findGuestInteractive();
                break;
            case "5":
                updateBudget();
                break;
            case "6":
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        System.out.println();
        return true;
    }

    private static void addGuestInteractive() {
        System.out.print("Enter guest name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        // Check if guest already exists
        if (guestListManager.findGuest(name) != null) {
            System.out.println("A guest with this name already exists.");
            return;
        }

        System.out.print("Enter group tag (e.g., family, friends, coworkers): ");
        String groupTag = scanner.nextLine().trim();

        Guest guest = new Guest(name, groupTag);
        guestListManager.addGuest(guest);
        System.out.println("Guest '" + name + "' added successfully!");
    }

    private static void removeGuestInteractive() {
        if (guestListManager.getGuestCount() == 0) {
            System.out.println("No guests to remove.");
            return;
        }

        System.out.print("Enter the name of the guest to remove: ");
        String name = scanner.nextLine().trim();

        if (guestListManager.removeGuest(name)) {
            System.out.println("Guest '" + name + "' removed successfully!");
        } else {
            System.out.println("Guest '" + name + "' not found.");
        }
    }

    private static void viewAllGuests() {
        if (guestListManager.getGuestCount() == 0) {
            System.out.println("No guests in the list.");
            return;
        }

        System.out.println("========== GUEST LIST ==========");
        int index = 1;
        for (Guest guest : guestListManager.getAllGuests()) {
            System.out.println(index + ". " + guest.getName() +
                             " (Group: " + guest.getGroupTag() + ")");
            index++;
        }
        System.out.println("================================");
        System.out.println("Total: " + guestListManager.getGuestCount() + " guest(s)");
    }

    private static void findGuestInteractive() {
        System.out.print("Enter guest name to search: ");
        String name = scanner.nextLine().trim();

        Guest guest = guestListManager.findGuest(name);
        if (guest != null) {
            System.out.println("Guest found:");
            System.out.println("  Name: " + guest.getName());
            System.out.println("  Group: " + guest.getGroupTag());
        } else {
            System.out.println("Guest '" + name + "' not found.");
        }
    }

    private static void updateBudget() {
        System.out.println("Current budget: $" + String.format("%.2f", budget));
        System.out.print("Enter new budget: $");

        try {
            String input = scanner.nextLine().trim();
            double newBudget = Double.parseDouble(input);
            if (newBudget > 0) {
                budget = newBudget;
                System.out.println("Budget updated to: $" + String.format("%.2f", budget));
            } else {
                System.out.println("Budget must be positive.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Budget not updated.");
        }
    }
}
