package searching_text_string;
import java.util.Scanner;

public class BoyerMooreSearch {

    // List of all 50 states in the United States as input text
    private static final String STATES_TEXT = String.join(" ", new String[]{
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
            "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
            "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", 
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
            "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", 
            "Wisconsin", "Wyoming"
    });

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display the menu
            System.out.println("\nMenu:");
            System.out.println("1) Display the text");
            System.out.println("2) Search");
            System.out.println("3) Exit program");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Display all 50 states
                    displayText();
                    break;
                case 2:
                    // Prompt user for a search pattern and perform the search
                    System.out.print("Enter part of the state name to search for: ");
                    String pattern = scanner.nextLine();
                    search(pattern);
                    break;
                case 3:
                    // Exit the program
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to display the list of all 50 states
    private static void displayText() {
        System.out.println("\nText Content:");
        System.out.println(STATES_TEXT);
    }

    // Method to perform the Boyer-Moore search
    private static void search(String pattern) {
        System.out.println("\nSearching for: \"" + pattern + "\"");
        int[] badCharTable = buildBadCharTable(pattern); // Create bad character table
        int textLength = STATES_TEXT.length();
        int patternLength = pattern.length();
        boolean found = false;

        // Boyer-Moore search algorithm
        int shift = 0;
        while (shift <= textLength - patternLength) {
            int j = patternLength - 1;

            // Compare pattern with the text from the end of the pattern
            while (j >= 0 && pattern.charAt(j) == STATES_TEXT.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                // If pattern is found
                System.out.println("Pattern found at index: " + shift);
                found = true;

                // Shift pattern to the right for the next possible match
                shift += (shift + patternLength < textLength) ? patternLength - badCharTable[STATES_TEXT.charAt(shift + patternLength)] : 1;
            } else {
                // Shift the pattern based on the bad character table
                shift += Math.max(1, j - badCharTable[STATES_TEXT.charAt(shift + j)]);
            }
        }

        if (!found) {
            // If no matches are found
            System.out.println("Pattern not found in the text.");
        }
    }

    // Method to build the bad character table
    private static int[] buildBadCharTable(String pattern) {
        int[] badCharTable = new int[256]; // Table for all ASCII characters

        // Initialize all characters with -1
        for (int i = 0; i < 256; i++) {
            badCharTable[i] = -1;
        }

        // Fill the table with the last occurrence of each character in the pattern
        for (int i = 0; i < pattern.length(); i++) {
            badCharTable[pattern.charAt(i)] = i;
        }

        return badCharTable;
    }
}

