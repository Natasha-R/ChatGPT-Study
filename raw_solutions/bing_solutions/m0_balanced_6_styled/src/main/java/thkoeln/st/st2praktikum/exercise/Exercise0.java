package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int[][] walls = {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {7, 2}, {7, 1}, {7, 0}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}, {8, 4}};
    private int[] robotPosition = {3, 0};
    private int[] roomSize = {12, 8};

    public String walkTo(String walkCommandString) {
        String[] walkCommand = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = walkCommand[0];
        int steps = Integer.parseInt(walkCommand[1]);
        int[] newPosition = new int[2];
        newPosition[0] = robotPosition[0];
        newPosition[1] = robotPosition[1];

        if (direction.equals("no")) {
            newPosition[1] += steps;
        } else if (direction.equals("ea")) {
            newPosition[0] += steps;
        } else if (direction.equals("so")) {
            newPosition[1] -= steps;
        } else if (direction.equals("we")) {
            newPosition[0] -= steps;
        } else {
            return "Invalid direction";
        }

        if (newPosition[0] < 0 || newPosition[0] >= roomSize[0] || newPosition[1] < 0 || newPosition[1] >= roomSize[1]) {
            return "(" + robotPosition[0] + "," + robotPosition[1] + ")";
        }

        for (int[] wall : walls) {
            if (newPosition[0] == wall[0] && newPosition[1] == wall[1]) {
                return "(" + robotPosition[0] + "," + robotPosition[1] + ")";
            }
        }

        robotPosition = newPosition;

        return "(" + robotPosition[0] + "," + robotPosition[1] + ")";
    }
}