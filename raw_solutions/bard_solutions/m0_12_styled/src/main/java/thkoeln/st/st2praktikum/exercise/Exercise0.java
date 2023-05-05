package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    private static final int W = 11;
    private static final int H = 8;
    private static final int Sx = 7;
    private static final int Sy = 7;
    private static final int[][] B = {{2, 1, 10, 1}, {2, 1, 2, 6}, {2, 6, 7, 6}, {10, 1, 10, 8}};
    private int x = Sx;
    private int y = Sy;
    public Exercise0() {}
    @Override
    public String move(String moveCommandString) {
        String[] tokens = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String d = tokens[0];
        int s = Integer.parseInt(tokens[1]);
        if (d.equals("no")) {
            for (int i = 0; i < s; i++) {
                if (y - 1 < 0 || isBarrier(x, y - 1)) {
                    break;
                }
                y--;
            }
        } else if (d.equals("ea")) {
            for (int i = 0; i < s; i++) {
                if (x + 1 >= W || isBarrier(x + 1, y)) {
                    break;
                }
                x++;
            }
        } else if (d.equals("so")) {
            for (int i = 0; i < s; i++) {
                if (y + 1 >= H || isBarrier(x, y + 1)) {
                    break;
                }
                y++;
            }
        } else if (d.equals("we")) {
            for (int i = 0; i < s; i++) {
                if (x - 1 < 0 || isBarrier(x - 1, y)) {
                    break;
                }
                x--;
            }
        }
        return "(" + x + "," + y + ")";
    }
    private boolean isBarrier(int x, int y) {
        for (int[] barrier : B) {
            if (x >= barrier[0] && x <= barrier[2] && y >= barrier[1] && y <= barrier[3]) {
                return true;
            }
        }
        return false;
    }
}