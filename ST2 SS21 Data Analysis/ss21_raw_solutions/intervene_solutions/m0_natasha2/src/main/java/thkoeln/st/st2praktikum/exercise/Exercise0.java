package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements GoAble {

    private int x, y;
    private final int width, height;
    private final Set<String> invalidMoves;

    public Exercise0() {
        // droid initial position
        this.x = 11;
        this.y = 7;

        // spaceship deck dimensions
        this.width = 12;
        this.height = 8;

        // obstacles
        int[][][] obstacles = {
                {{6, 2}, {6, 5}},
                {{5, 5}, {6, 5}},
                {{5, 5}, {5, 6}},
                {{5, 6}, {12, 6}}
        };

        // generate invalid moves
        this.invalidMoves = new HashSet<>();
        for (int[][] obstacle : obstacles) {
            invalidMoves.addAll(generateObstaclePairs(obstacle));
        }
    }

    private List<String> generateObstaclePairs(int[][] obstacle) {
        List<String> obstaclePairs = new ArrayList<>();

        int startX = obstacle[0][0];
        int startY = obstacle[0][1];
        int endX = obstacle[1][0];
        int endY = obstacle[1][1];

        // Vertical obstacle
        if (startX == endX) {
            int minY = Math.min(startY, endY);
            int maxY = Math.max(startY, endY);

            for (int y = minY; y < maxY; y++) {
                obstaclePairs.add((startX - 1) + "," + y + "-" + startX + "," + y);
                obstaclePairs.add(startX + "," + y + "-" + (startX - 1) + "," + y);
            }
        }
        // Horizontal obstacle
        else if (startY == endY) {
            int minX = Math.min(startX, endX);
            int maxX = Math.max(startX, endX);

            for (int x = minX; x < maxX; x++) {
                obstaclePairs.add(x + "," + (startY - 1) + "-" + x + "," + startY);
                obstaclePairs.add(x + "," + startY + "-" + x + "," + (startY - 1));
            }
        }

        return obstaclePairs;
    }

    @Override
    public String goTo(String goCommandString) {
        // remove brackets and split by comma
        String[] command = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        String direction = command[0];
        int steps = Integer.parseInt(command[1]);

        for (int i = 0; i < steps; i++) {
            int oldX = x, oldY = y;

            switch (direction) {
                case "no":
                    if (y + 1 < height) y++;
                    break;
                case "ea":
                    if (x + 1 < width) x++;
                    break;
                case "so":
                    if (y - 1 >= 0) y--;
                    break;
                case "we":
                    if (x - 1 >= 0) x--;
                    break;
            }

            if (!isValidMove(oldX, oldY, x, y)) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isValidMove(int oldX, int oldY, int newX, int newY) {
        String move = oldX + "," + oldY + "-" + newX + "," + newY;
        return !invalidMoves.contains(move);
    }
}
