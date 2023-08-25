package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 0;
    private int y = 2;
    private final int fieldWidth = 12;
    private final int fieldHeight = 9;

    @Override
    public String walk(String walkCommandString) {
        String[] parts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = parts[0].trim();
        int steps = Integer.parseInt(parts[1].trim());

        switch (direction) {
            case "no":
                y += steps;
                if (x >= 4 && x < 7 && y > 4) y = 4;
                if (y >= fieldHeight) y = fieldHeight - 1;
                break;

            case "ea":
                x += steps;
                if (y >= 0 && y < 3 && x > 2) x = 2;
                if (y >= 0 && y < 4 && x > 4) x = 4;
                if (y > 4 && y <= 8 && x > 6) x = 6;
                if (x >= fieldWidth) x = fieldWidth - 1;
                break;

            case "so":
                y -= steps;
                if (x >= 4 && x < 7 && y < 5 && y > 0) y = 5;
                if (x >= 3 && x <= 5 && y >= 3) y = 3;
                if (y < 0) y = 0;
                break;

            case "we":
                x -= steps;
                if (x < 0) x = 0;
                break;
        }

        return "(" + x + "," + y + ")";
    }
}