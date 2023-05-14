package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {
    private Point pos;
    private int w = 12;
    private int h = 9;
    private List<Point> ws = Arrays.asList(new Point(3, 0), new Point(5, 0), new Point(4, 5), new Point(7, 5));
    private List<Point> we = Arrays.asList(new Point(3, 3), new Point(5, 4), new Point(7, 5), new Point(7, 9));

    public Exercise0() {
        this.pos = new Point(0, 2);
    }

    @Override
    public String walk(String cmd) {
        String dir = cmd.substring(1, 3);
        int s = Integer.parseInt(cmd.substring(4, cmd.length() - 1));
        Point np;

        if (dir.equals("no")) {
            for (int i = 0; i < s; i++) {
                np = new Point(pos.x, pos.y + 1);
                if (canMove(np)) pos = np;
            }
        } else if (dir.equals("ea")) {
            for (int i = 0; i < s; i++) {
                np = new Point(pos.x + 1, pos.y);
                if (canMove(np)) pos = np;
            }
        } else if (dir.equals("so")) {
            for (int i = 0; i < s; i++) {
                np = new Point(pos.x, pos.y - 1);
                if (canMove(np)) pos = np;
            }
        } else if (dir.equals("we")) {
            for (int i = 0; i < s; i++) {
                np = new Point(pos.x - 1, pos.y);
                if (canMove(np)) pos = np;
            }
        }

        return "(" + pos.x + "," + pos.y + ")";
    }

    private boolean canMove(Point np) {
        if (np.x < 0 || np.x >= w || np.y < 0 || np.y >= h) {
            return false;
        }

        for (int i = 0; i < ws.size(); i++) {
            if ((np.x == ws.get(i).x && np.y >= ws.get(i).y && np.y < we.get(i).y) ||
                    (np.y == ws.get(i).y && np.x >= ws.get(i).x && np.x < we.get(i).x)) {
                return false;
            }
        }

        return true;
    }
}