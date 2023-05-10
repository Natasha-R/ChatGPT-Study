package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 0;
    private int y = 2;
    private final int MAX_X = 11; // As the field is 12 tiles wide (0-11)
    private final int MAX_Y = 8; // As the field is 9 tiles high (0-8)

    @Override
    public String walk(String walkCommandString) {
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        for(int i = 0; i < steps; i++) {
            if ("no".equals(direction)) {
                if (y < MAX_Y && !(x >= 4 && x < 7 && y == 4)) {
                    y++;
                }
            } else if ("ea".equals(direction)) {
                if (x < MAX_X && !(y >= 0 && y < 4 && x == 2) && !(y > 0 && y <= 4 && x == 4) && !(y >= 5 && x == 6)) {
                    x++;
                }
            } else if ("so".equals(direction)) {
                if (y > 0 && !(x >= 4 && x < 7 && y == 5) && !(x == 7 && y == 6)) {
                    y--;
                }
            } else if ("we".equals(direction)) {
                if (x > 0 && !(y >= 0 && y < 4 && x == 3) && !(y > 0 && y <= 4 && x == 5)) {
                    x--;
                }
            }
        }

        return "(" + x + "," + y + ")";
    }
}
