package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private int x = 0;
    private int y = 2;
    private final int maxX = 11;
    private final int maxY = 8;
    private final int[][] walls = {
            {3, 0, 3, 3},
            {5, 0, 5, 4},
            {4, 5, 7, 5},
            {7, 5, 7, 9}
    };

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0].trim();
        int steps = Integer.parseInt(commandParts[1].trim());

        switch (direction) {
            case "no":
                y += steps;
                for (int[] wall : walls) {
                    if (wall[0] == wall[2] && y >= wall[1] && y <= wall[3] && x == wall[0] - 1) {
                        y = wall[1] - 1;
                        break;
                    }
                }
                if (y > maxY) y = maxY;
                break;

            case "ea":
                x += steps;
                for (int[] wall : walls) {
                    if (wall[0] == wall[2] && x == wall[0] && y >= wall[1] - 1 && y < wall[3]) {
                        x = wall[0] - 1;
                        break;
                    }
                }
                if (x > maxX) x = maxX;
                break;

            case "so":
                y -= steps;
                for (int[] wall : walls) {
                    if (wall[0] == wall[2] && y <= wall[3] && y >= wall[1] && x == wall[0] - 1) {
                        y = wall[3] + 1;
                        break;
                    }
                }
                if (y < 0) y = 0;
                break;

            case "we":
                x -= steps;
                for (int[] wall : walls) {
                    if (wall[0] == wall[2] && x == wall[2] && y >= wall[1] - 1 && y < wall[3]) {
                        x = wall[2] + 1;
                        break;
                    }
                }
                if (x < 0) x = 0;
                break;
        }

        return String.format("(%d,%d)", x, y);
    }
}