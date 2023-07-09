package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x, y;
    private final int fieldWidth = 12;
    private final int fieldHeight = 9;

    public Exercise0() {
        x = 0;
        y = 2;
    }

    @Override
    public String walk(String walkCommandString) {
        String[] parts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");

        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);
        int newX = x;
        int newY = y;

        switch (direction) {
            case "no":
                newY += steps;
                if (newY >= fieldHeight) {
                    newY = fieldHeight - 1;
                }
                if (newX >= 4 && newX <= 7 && newY > 5) {
                    newY = 5;
                }
                break;
            case "ea":
                newX += steps;
                if (newX >= fieldWidth) {
                    newX = fieldWidth - 1;
                }
                if (newY >= 0 && newY <= 3 && newX > 3) {
                    newX = 3;
                }
                if (newY >= 0 && newY <= 4 && newX > 5) {
                    newX = 5;
                }
                break;
            case "so":
                newY -= steps;
                if (newY < 0) {
                    newY = 0;
                }
                if (newX >= 4 && newX <= 7 && newY < 5 && newY >= 0) {
                    newY = 5;
                }
                break;
            case "we":
                newX -= steps;
                if (newX < 0) {
                    newX = 0;
                }
                if (newY >= 0 && newY <= 3 && newX < 3) {
                    newX = 3;
                }
                if (newY >= 0 && newY <= 4 && newX < 5) {
                    newX = 5;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        x = newX;
        y = newY;
        return "(" + x + "," + y + ")";
    }
}
