public class GettingOrientation {
    // Method to calculate and display the orientation of the robot
    public void calculateRobotOrientation(int[][] shortestPath, int startRow, int startCol, int endRow, int endCol) {
        // Extracting the next position from the shortest path array
        int nextRow = shortestPath[1][0];
        int nextCol = shortestPath[1][1];
        // Calculating the starting orientation of the robot
        String startOrientation = calculateOrientation(startRow, startCol, nextRow, nextCol);

        // Extracting the previous position from the shortest path array
        int prevRow = shortestPath[shortestPath.length - 2][0];
        int prevCol = shortestPath[shortestPath.length - 2][1];
        // Calculating the ending orientation of the robot
        String endOrientation = calculateOrientation(prevRow, prevCol, endRow, endCol);

        // Displaying starting robot state
        System.out.println("\n>> Starting Robot State <<");
        System.out.printf("\tStarting robot position: (%d,%d)\n", startRow, startCol);
        System.out.printf("\tStarting robot orientation: %s\n", startOrientation);

        // Displaying ending robot state
        System.out.println("\n>> Ending Robot State <<");
        System.out.printf("\tEnding robot position: (%d,%d)\n", endRow, endCol);
        System.out.printf("\tEnding robot orientation: %s\n", endOrientation);
    }

    // Method to calculate the orientation based on the relative positions of two points
    private String calculateOrientation(int row1, int col1, int row2, int col2) {
        if (row1 < row2) {
            if (col1 < col2)
                return "South-East";
            else if (col1 > col2)
                return "South-West";
            else
                return "South";
        } else if (row1 > row2) {
            if (col1 < col2)
                return "North-East";
            else if (col1 > col2)
                return "North-West";
            else
                return "North";
        } else {
            if (col1 < col2)
                return "East";
            else if (col1 > col2)
                return "West";
            else
                return "Unknown";
        }
    }
}
