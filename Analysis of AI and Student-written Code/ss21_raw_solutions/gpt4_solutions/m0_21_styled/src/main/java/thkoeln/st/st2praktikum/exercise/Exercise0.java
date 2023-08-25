package thkoeln.st.st2praktikum.exercise;
import java.awt.Point;
public class Exercise0 implements Walkable {
    private static final int F_W = 12;
    private static final int F_H = 9;
    boolean[][] f = new boolean[F_H][F_W];
    Point m;

    public Exercise0() {
        m = new Point(0, 2);
        initF();
    }

    private void initF() {
        for (int i = 0; i < F_H; i++) {
            for (int j = 0; j < F_W; j++) {
                f[i][j] = true;
            }
        }
        bW(3, 0, 3, 3);
        bW(5, 0, 5, 4);
        bW(4, 5, 7, 5);
        bW(7, 5, 7, 9);
    }

    private void bW(int startX, int startY, int endX, int endY) {
        for (int i = Math.min(startY, endY); i <= Math.max(startY, endY); i++) {
            for (int j = Math.min(startX, endX); j <= Math.max(startX, endX); j++) {
                f[i][j] = false;
            }
        }
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.replace("[", "").replace("]", "").split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    if (m.y < F_H - 2 && f[m.y + 2][m.x]) {
                        m.y++;
                    }
                    break;
                case "ea":
                    if (m.x < F_W - 2 && f[m.y][m.x + 2]) {
                        m.x++;
                    }
                    break;
                case "so":
                    if (m.y > 1 && f[m.y - 2][m.x]) {
                        m.y--;
                    }
                    break;
                case "we":
                    if (m.x > 1 && f[m.y][m.x - 2]) {
                        m.x--;
                    }
                    break;
            }
        }

        return "(" + m.x + "," + m.y + ")";
    }
}

