package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    private final int maxX = 12;
    private final int maxY = 9;
    private final int[][] walls = {
            {3, 0, 3, 3},
            {5, 0, 5, 4},
            {4, 5, 7, 5},
            {7, 5, 7, 9},
    };

    public Exercise0() {
        this.x = 0;
        this.y = 2;
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.replace("[", "").replace("]", "").split(",");
        String direction = commandParts[0].trim();
        int steps = Integer.parseInt(commandParts[1].trim());

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (canMove(x, y + 1)) {
                        y++;
                    } else {
                        break;
                    }
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (canMove(x + 1, y)) {
                        x++;
                    } else {
                        break;
                    }
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (canMove(x, y - 1)) {
                        y--;
                    } else {
                        break;
                    }
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (canMove(x - 1, y)) {
                        x--;
                    } else {
                        break;
                    }
                }
                break;
        }

        return "(" + x + "," + y + ")";
    }

    private boolean canMove(int newX, int newY) {
        if (newX < 0 || newY < 0 || newX >= maxX || newY >= maxY) {
            return false;
        }

        for (int[] wall : walls) {
            if ((wall[0] == wall[2] && wall[0] == newX && ((newY >= wall[1] && newY < wall[3]) || (newY + 1 > wall[1] && newY + 1 <= wall[3]))) ||
                    (wall[1] == wall[3] && wall[1] == newY + 1 && ((newX >= wall[0] && newX < wall[2]) || (newX + 1 > wall[0] && newX + 1 <= wall[2])))) {
                return false;
            }
        }

        return true;
    }
}