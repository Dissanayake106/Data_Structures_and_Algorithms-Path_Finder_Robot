import java.util.Random;
import java.util.Scanner;

public class Grid {
    // 2D array to represent obstacles in the grid
    private boolean[][] obstacles;
    // 2D array to store the shortest path found
    int[][] shortestPath;
    private int rows;
    private int columns;

    // Constructor to initialize the grid with a specified number of rows and columns, and a density of obstacles
    public Grid(int rows, int columns, double obstacleDensity) {
        this.rows = rows;
        this.columns = columns;
        obstacles = new boolean[rows][columns];
        shortestPath = new int[rows * columns][2];
        placeObstacles(obstacleDensity);
    }

    // Method to randomly place obstacles in the grid based on the given density
    private void placeObstacles(double obstacleDensity) {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                obstacles[i][j] = random.nextDouble() <= obstacleDensity;
            }
        }
    }

    // Method to print the grid, highlighting the start and end points and obstacles
    public void print(int startRow, int startCol, int endRow, int endCol) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("+" + "-".repeat(7));
            }
            System.out.println("+");

            for (int j = 0; j < columns; j++) {
                if (i == startRow && j == startCol) {
                    System.out.print("| Robot ");
                } else if (i == endRow && j == endCol) {
                    System.out.print("| End   ");
                } else if (obstacles[i][j]) {
                    System.out.print("| XXX   ");
                } else {
                    System.out.print("|       ");
                }
            }
            System.out.println("|");
        }
        for (int j = 0; j < columns; j++) {
            System.out.print("+" + "-".repeat(7));
        }
        System.out.println("+");
    }

    // Method to display the grid along with the shortest path found
    public void displayWithPath(int startRow, int startCol, int endRow, int endCol) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("+" + "-".repeat(7));
            }
            System.out.println("+");

            for (int j = 0; j < columns; j++) {
                if (i == startRow && j == startCol) {
                    System.out.print("| Robot ");
                } else if (i == endRow && j == endCol) {
                    System.out.print("|  End  ");
                } else if (obstacles[i][j]) {
                    System.out.print("|  XXX  ");
                } else if (isInShortestPath(i, j)) {
                    System.out.print("|  =>   ");
                } else {
                    System.out.print("|       ");
                }
            }
            System.out.println("|");
        }
        for (int j = 0; j < columns; j++) {
            System.out.print("+" + "-".repeat(7));
        }
        System.out.println("+");

        System.out.println("\nThe Shortest Path:");
        for (int i = 0; i < shortestPath.length; i++) {
            int row = shortestPath[i][0];
            int col = shortestPath[i][1];
            System.out.printf("(%d,%d) ", row, col);
        }
        System.out.println();
    }

    // Helper method to check if a cell is part of the shortest path
    private boolean isInShortestPath(int row, int col) {
        for (int[] cell : shortestPath) {
            if (cell[0] == row && cell[1] == col) {
                return true;
            }
        }
        return false;
    }

    // Method to find the shortest path from start to end, avoiding obstacles
    public boolean findShortestPath(int startRow, int startCol, int endRow, int endCol) {
        boolean[][] visited = new boolean[rows][columns];
        int[][] parent = new int[rows][columns];
        CustomQueue queue = new CustomQueue(rows * columns);

        queue.enqueue(startRow * columns + startCol);
        visited[startRow][startCol] = true;

        int[] dx = {0, 0, 1, -1, 1, -1, 1, -1};
        int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            int row = current / columns;
            int col = current % columns;
            if (row == endRow && col == endCol) {
                shortestPath = reconstructPath(parent, startRow, startCol, endRow, endCol);
                return true;
            }
            for (int i = 0; i < 8; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];
                if (isValid(newRow, newCol) && !obstacles[newRow][newCol] && !visited[newRow][newCol]) {
                    queue.enqueue(newRow * columns + newCol);
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                }
            }
        }
        return false;
    }

    // Helper method to check if a cell is within the grid boundaries
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

    // Method to reconstruct the shortest path from the parent array
    private int[][] reconstructPath(int[][] parent, int startRow, int startCol, int endRow, int endCol) {
        int[][] path = new int[rows * columns][2];
        int[] current = {endRow, endCol};
        int index = 0;
        while (current[0] != startRow || current[1] != startCol) {
            path[index++] = current;
            int row = current[0];
            int col = current[1];
            current = new int[]{parent[row][col] / columns, parent[row][col] % columns};
        }
        path[index++] = new int[]{startRow, startCol};
        int[][] trimmedPath = new int[index][2];
        for (int i = 0; i < index; i++) {
            trimmedPath[i] = path[index - i - 1];
        }
        return trimmedPath;
    }

    // Custom queue implementation for breadth-first search
    static class CustomQueue {
        private int[] arr;
        private int front, rear, size;

        public CustomQueue(int capacity) {
            arr = new int[capacity];
            front = 0;
            rear = -1;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void enqueue(int item) {
            if (size == arr.length) {
                throw new IllegalStateException("Queue is full");
            }
            rear = (rear + 1) % arr.length;
            arr[rear] = item;
            size++;
        }

        public int dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue is empty");
            }
            int removedItem = arr[front];
            front = (front + 1) % arr.length;
            size--;
            return removedItem;
        }
    }
}
