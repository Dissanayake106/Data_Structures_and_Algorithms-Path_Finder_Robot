import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean runAgain = true;

        // Welcome message and instructions
        System.out.println("\n=-=-=-=-=-=-=-=-=-=Welcome to Path Planning Robot=-=-=-=-=-=-=-=-=-=\n\t\tImportant:\n\t\t\t " +
                "For clarity, the range from 1 to 15 is only integer, Insert into Rows and Columns.");

        // Main loop for running the robot
        while (runAgain) {
            int rows = 0, columns = 0;
            double obstacleDensity = 0.2;

            try {
                // Grid creation phase
                while (true) {
                    System.out.print("\n---Create Grid---\n\tEnter number of rows: ");
                    if (scanner.hasNextInt()) {
                        rows = scanner.nextInt();
                        if (rows <= 0) {
                            System.out.println("\n\t\t><><><>< Invalid input, Please enter valid positive integer for Rows! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\n\t\t><><><>< Invalid input, Please enter valid positive integer for Rows! ><><><><\n");
                        scanner.next();
                    }
                }

                while (true) {
                    System.out.print("\tEnter number of columns: ");
                    if (scanner.hasNextInt()) {
                        columns = scanner.nextInt();
                        if (columns <= 0) {
                            System.out.println("\n\t\t><><><>< Invalid input, Please enter valid positive integer for Columns! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\n\t\t><><><>< Invalid input, Please enter valid positive integer for Columns! ><><><><\n");
                        scanner.next();
                    }
                }

                // Grid object creation
                Grid grid = new Grid(rows, columns, obstacleDensity);

                // Starting position input phase
                int startRow = -1, startCol = -1;
                while (true) {
                    System.out.print("\n---Starting Rows And Columns---\n\tEnter the Row number of Starting Robot Position (0-" + (rows - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        startRow = scanner.nextInt();
                        if (startRow < 0 || startRow >= rows) {
                            System.out.println("\t\t><><><>< Invalid input, Please enter a valid row number within the range! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\t\t><><><>< Invalid input, Please enter a valid positive integer for Row! ><><><><\n");
                        scanner.next();
                    }
                }

                while (true) {
                    System.out.print("\tEnter the Column number of Starting Robot Position (0-" + (columns - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        startCol = scanner.nextInt();
                        if (startCol < 0 || startCol >= columns) {
                            System.out.println("\t\t><><><>< Invalid input, Please enter a valid column number within the range! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\t\t><><><>< Invalid input, Please enter a valid positive integer for Column! ><><><><\n");
                        scanner.next();
                    }
                }

                // Ending position input phase
                int endRow = -1, endCol = -1;
                while (true) {
                    System.out.print("\n---Ending Rows And Columns---\n\tEnter the Row number of Ending Robot Position (0-" + (rows - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        endRow = scanner.nextInt();
                        if (endRow < 0 || endRow >= rows) {
                            System.out.println("\t\t><><><>< Invalid input, Please enter a valid row number within the range! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\t\t><><><>< Invalid input, Please enter a valid positive integer for Row! ><><><><\n");
                        scanner.next();
                    }
                }

                while (true) {
                    System.out.print("\tEnter the Column number of Ending Robot Position (0-" + (columns - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        endCol = scanner.nextInt();
                        if (endCol < 0 || endCol >= columns) {
                            System.out.println("\t\t><><><>< Invalid input, Please enter a valid column number within the range! ><><><><\n");
                            continue;
                        }
                        break;
                    } else {
                        System.out.println("\t\t><><><>< Invalid input, Please enter a valid positive integer for Column! ><><><><\n");
                        scanner.next();
                    }
                }

                // Validation for starting and ending positions
                if ((endRow < 0 || endRow >= rows || endCol < 0 || endCol >= columns) || (startRow == endRow && startCol == endCol)) {
                    if (startRow == endRow && startCol == endCol) {
                        System.out.println("\t\t>>>>Starting and ending positions cannot be the same! Please enter different positions.<<<<");
                    } else {
                        System.out.println("\t\t>>>>Invalid ending position! Please enter valid positions within the grid.<<<<\n");
                    }
                    continue;
                }

                // Display grid with obstacles and without shortest path
                System.out.println("\n\nGrid with obstacles and without shortest path:");
                grid.print(startRow, startCol, endRow, endCol);

                // Finding and displaying shortest path
                if (!grid.findShortestPath(startRow, startCol, endRow, endCol)) {
                    System.out.println("No path found.");
                } else {
                    System.out.println("\nGrid with obstacles and shortest path:");
                    grid.displayWithPath(startRow, startCol, endRow, endCol);
                    GettingOrientation orientation = new GettingOrientation();
                    orientation.calculateRobotOrientation(grid.shortestPath, startRow, startCol, endRow, endCol);
                }
            } finally {
                // Asking if user wants to run the robot again
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("\nDo you want to run the robot again? (yes/no): ");
                    String runAgainInput = scanner.next().trim().toLowerCase();
                    if ("no".equals(runAgainInput)) {
                        runAgain = false;
                        validInput = true;
                    } else if ("yes".equals(runAgainInput)) {
                        runAgain = true;
                        validInput = true;
                    } else {
                        System.out.println("\n>>>>>Invalid input. Please enter 'yes' or 'no'.<<<<<");
                    }
                }
            }
        }
        System.out.println("\n\t\t>>>>>Exit From Robot...Good Bye!<<<<<");
        scanner.close();
    }
}
