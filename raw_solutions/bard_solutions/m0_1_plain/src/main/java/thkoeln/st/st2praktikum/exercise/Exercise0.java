package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private String[] barriers = {"(2,1)-(10,1)", "(2,1)-(2,6)", "(2,6)-(7,6)", "(10,1)-(10,8)"};

    @Override
    public String move(String moveCommandString) {
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        if (direction.equals("no")) {
            for (int i = 0; i < steps; i++) {
                if (y > 0 && !isBarrier(x, y - 1)) {
                    y--;
                } else {
                    break;
                }
            }
        } else if (direction.equals("ea")) {
            for (int i = 0; i < steps; i++) {
                if (x < width - 1 && !isBarrier(x + 1, y)) {
                    x++;
                } else {
                    break;
                }
            }
        } else if (direction.equals("so")) {
            for (int i = 0; i < steps; i++) {
                if (y < height - 1 && !isBarrier(x, y + 1)) {
                    y++;
                } else {
                    break;
                }
            }
        } else if (direction.equals("we")) {
            for (int i = 0; i < steps; i++) {
                if (x > 0 && !isBarrier(x - 1, y)) {
                    x--;
                } else {
                    break;
                }
            }
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isBarrier(int x, int y) {
        for (String barrier : barriers) {
            String[] parts = barrier.replace("(", "").replace(")", "").replace(",", "").split("-");
            int x1 = Integer.parseInt(parts[0].substring(1, parts[0].length()));
            int y1 = Integer.parseInt(parts[0].substring(parts[0].length() - 1));
            int x2 = Integer.parseInt(parts[1].substring(1, parts[1].length()));
            int y2 = Integer.parseInt(parts[1].substring(parts[1].length() - 1));
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                return true;
            }
        }
        return false;
    }
}