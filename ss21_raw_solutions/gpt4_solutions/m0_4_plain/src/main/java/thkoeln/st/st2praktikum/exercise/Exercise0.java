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
        int newX = x;
        int newY = y;

        switch (direction) {
            case "no":
                newY = Math.min(y + steps, height - 1);
                break;
            case "ea":
                newX = Math.min(x + steps, width - 1);
                break;
            case "so":
                newY = Math.max(y - steps, 0);
                break;
            case "we":
                newX = Math.max(x - steps, 0);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Check for walls
        if (!((newX >= 3 && newX <= 5 && newY == 3) || (newX >= 4 && newX <= 7 && newY == 5))) {
            x = newX;
            y = newY;
        }

        return "(" + x + "," + y + ")";
    }
}
