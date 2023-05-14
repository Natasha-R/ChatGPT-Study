package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int[][] walls = {{0, 0, 12, 0}, {0, 0, 0, 8}, {12, 0, 12, 8}, {0, 8, 12, 8}, {3, 0, 3, 3}, {4, 3, 7, 3}, {7, 0, 7, 2}, {1, 4, 8, 4}};
    private int[] robotPosition = {3, 0};
    private int[] roomSize = {12, 8};

    public Exercise0() {
    }

    public String walkTo(String walkCommandString) {
        String[] walkCommand = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = walkCommand[0];
        int steps = Integer.parseInt(walkCommand[1]);

        int[] newPosition = new int[]{robotPosition[0], robotPosition[1]};
        switch (direction) {
            case "no":
                newPosition[1] += steps;
                break;
            case "ea":
                newPosition[0] += steps;
                break;
            case "so":
                newPosition[1] -= steps;
                break;
            case "we":
                newPosition[0] -= steps;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        if (newPosition[0] < 0) {
            newPosition[0] = 0;
        } else if (newPosition[0] >= roomSize[0]) {
            newPosition[0] = roomSize[0] - 1;
        }

        if (newPosition[1] < 0) {
            newPosition[1] = 0;
        } else if (newPosition[1] >= roomSize[1]) {
            newPosition[1] = roomSize[1] - 1;
        }

        for (int[] wall : walls) {
            if ((wall[0] == robotPosition[0] && wall[2] == robotPosition[0]) || (wall[2] == robotPosition[0] && wall[4] == robotPosition[1])) {
                if ((wall[1] <= robotPosition[1] && robotPosition[1] <= wall[3]) || (wall[3] <= robotPosition[1] && robotPosition[1] <= wall[5])) {
                    if ((wall[1] <= newPosition[1] && newPosition[1] <= wall[3]) || (wall[3] <= newPosition[1] && newPosition[1] <= wall[5])) {
                        if ((wall [2]== wall [4]) && (robotPosition [0]!= wall [2])) {
                            continue;
                        }
                        if ((wall [3]== wall [5]) && (robotPosition [1]!= wall [3])) {
                            continue;
                        }
                        return "(" + robotPosition [0]+","+robotPosition [1]+")";
                    }
                }
            }
        }

        robotPosition = newPosition;

        return "(" + robotPosition [0]+","+robotPosition [1]+")";
    }
}
