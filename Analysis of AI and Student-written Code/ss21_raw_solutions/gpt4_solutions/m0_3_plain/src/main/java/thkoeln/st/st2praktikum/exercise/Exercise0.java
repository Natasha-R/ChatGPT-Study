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
                y = Math.min(y + steps, height - 1);
                if (x == 3 && y > 3) {
                    y = 3;
                } else if (x == 7 && y < 5) {
                    y = 5;
                }
                break;
            case "ea":
                x = Math.min(x + steps, width - 1);
                if (y <= 3 && x > 3) {
                    x = 3;
                } else if (y <= 4 && x > 5) {
                    x = 5;
                } else if (y >= 5 && x < 7) {
                    x = 7;
                }
                break;
            case "so":
                y = Math.max(y - steps, 0);
                if (x == 3 && y < 3) {
                    y = 3;
                } else if (x == 7 && y > 5) {
                    y = 5;
                }
                break;
            case "we":
                x = Math.max(x - steps, 0);
                if (y <= 3 && x < 3) {
                    x = 3;
                } else if (y <= 4 && x < 5) {
                    x = 5;
                } else if (y >= 5 && x > 7) {
                    x = 7;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        return "(" + x + "," + y + ")";
    }
}
