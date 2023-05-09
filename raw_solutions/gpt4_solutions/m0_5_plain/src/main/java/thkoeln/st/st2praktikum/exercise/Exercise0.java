package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    private final int width = 12;
    private final int height = 9;

    public Exercise0() {
        this.x = 0;
        this.y = 2;
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0].trim();
        int steps = Integer.parseInt(commandParts[1].trim());

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y < height - 1 && !(x == 3 && y == 2) && !(x == 7 && y == 4)) {
                        y++;
                    }
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x < width - 1 && !(y <= 3 && x == 2) && !(y <= 4 && x == 4) && !(y >= 5 && x == 6)) {
                        x++;
                    }
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y > 0 && !(x == 3 && y == 4) && !(x == 7 && y == 6)) {
                        y--;
                    }
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x > 0 && !(y <= 3 && x == 4) && !(y <= 4 && x == 6) && !(y >= 5 && x == 8)) {
                        x--;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        return "(" + x + "," + y + ")";
    }
}