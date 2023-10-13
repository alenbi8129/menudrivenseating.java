import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuDrivenSeating {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] groupSizes = null;
        boolean programRunning = true;

        while (programRunning) {
            System.out.println("Menu:");
            System.out.println("1. Enter Group Sizes");
            System.out.println("2. Create Seating Plan");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    groupSizes = getInputData(scanner); // Get group sizes from the user
                    break;
                case 2:
                    if (groupSizes != null) {
                        int tableSize = getTableSize(scanner); // Get the table size
                        List<Table> seatingPlan = calculateSeatingPlan(groupSizes, tableSize); // Calculate the seating plan
                        displaySeatingPlan(seatingPlan); // Display the seating plan
                    } else {
                        System.out.println("Please enter group sizes first.");
                    }
                    break;
                case 3:
                    programRunning = false; // Exit the program
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        System.out.println("Program has been exited.");
        scanner.close(); // Close the scanner to release resources
    }

    // Function to get group sizes from the user
    public static int[] getInputData(Scanner scanner) {
        System.out.print("Enter the number of groups: ");
        int numGroups = scanner.nextInt();
        int[] groupSizes = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            System.out.print("Enter the size of group " + (i + 1) + ": ");
            groupSizes[i] = scanner.nextInt();
        }
        return groupSizes;
    }

    // Function to get the table size from the user
    public static int getTableSize(Scanner scanner) {
        System.out.print("Enter the size of each table: ");
        return scanner.nextInt();
    }

    // Function to calculate the seating plan
    public static List<Table> calculateSeatingPlan(int[] groupSizes, int tableSize) {
        Arrays.sort(groupSizes); // Sort group sizes in ascending order
        List<Table> seatingPlan = new ArrayList<>();

        for (int i = groupSizes.length - 1; i >= 0; i--) {
            int groupSize = groupSizes[i];
            boolean seated = false;

            for (Table table : seatingPlan) {
                if (table.getVacantSeats() >= groupSize) {
                    table.addGroup(groupSize);
                    seated = true;
                    break;
                }
            }

            if (!seated) {
                Table newTable = new Table(tableSize);
                newTable.addGroup(groupSize);
                seatingPlan.add(newTable);
            }
        }

        return seatingPlan;
    }

    // Function to display the seating plan
    public static void displaySeatingPlan(List<Table> seatingPlan) {
        for (Table table : seatingPlan) {
            System.out.println("Table Size: " + table.getSize());
            System.out.println("Group Sizes Seated: " + Arrays.toString(table.getSeatedGroups()));
            System.out.println("Vacant Seats: " + table.getVacantSeats());
            System.out.println();
        }
        System.out.println("Total Number of Tables: " + seatingPlan.size());
    }

    // Definition of the Table class
    static class Table {
        private int size;
        private List<Integer> seatedGroups;

        public Table(int size) {
            this.size = size;
            this.seatedGroups = new ArrayList<>();
        }

        public int getSize() {
            return size;
        }

        public int[] getSeatedGroups() {
            return seatedGroups.stream().mapToInt(Integer::intValue).toArray();
        }

        public int getVacantSeats() {
            return size - seatedGroups.stream().mapToInt(Integer::intValue).sum();
        }

        public void addGroup(int groupSize) {
            seatedGroups.add(groupSize);
        }
    }
}
