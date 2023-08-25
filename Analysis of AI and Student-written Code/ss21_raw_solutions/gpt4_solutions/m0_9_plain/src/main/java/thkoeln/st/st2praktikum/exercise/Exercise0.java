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
        String[] commands = walkCommandString.split(",");

        String direction = commands[0].substring(1);
        int steps = Integer.parseInt(commands[1].substring(0, commands[1].length() - 1));

        switch (direction) {
            case "no":
                y = Math.min(y + steps, fieldHeight - 1);
                if (x == 3 && y >= 3) y = 2;
                if (x == 5 && y <= 4) y = 5;
                if (x >= 4 && x <= 7 && y >= 5) y = 4;
                break;
            case "ea":
                x = Math.min(x + steps, fieldWidth - 1);
                if (y <= 3 && x >= 3) x = 2;
                if (y >= 5 && x >= 7) x = 6;
                break;
            case "so":
                y = Math.max(y - steps, 0);
                if (x == 3 && y <= 2) y = 3;
                if (x == 5 && y >= 4) y = 4;
                if (x >= 4 && x <= 7 && y <= 4) y = 5;
                break;
            case "we":
                x = Math.max(x - steps, 0);
                if (y <= 3 && x <= 2) x = 3;
                if (y >= 5 && x <= 6) x = 7;
                break;
        }

        return String.format("(%d,%d)", x, y);
    }
}