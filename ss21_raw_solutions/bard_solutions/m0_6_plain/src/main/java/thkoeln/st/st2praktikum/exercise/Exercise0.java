package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private int[][] barriers = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Exercise0() {
    }

    @Override
    public String move(String moveCommandString) {
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        if (direction.equals("no")) {
            for (int i = 0; i < steps; i++) {
                if (y - 1 < 0 || barriers[x][y - 1] == 1) {
                    break;
                }
                y--;
            }
        } else if (direction.equals("ea")) {
            for (int i = 0; i < steps; i++) {
                if (x + 1 >= width || barriers[x + 1][y] == 1) {
                    break;
                }
                x++;
            }
        } else if (direction.equals("so")) {
            for (int i = 0; i < steps; i++) {
                if (y + 1 >= height || barriers[x][y + 1] == 1) {
                    break;
                }
                y++;
            }
        } else if (direction.equals("we")) {
            for (int i = 0; i < steps; i++) {
                if (x - 1 < 0 || barriers[x - 1][y] == 1) {
                    break;
                }
                x--;
            }
        }

        return "(" + x + "," + y + ")";
    }
}