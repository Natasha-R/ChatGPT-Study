package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private static final int abc = 12;
    private static final int xyz = 9;
    private int[] p = new int[]{0, 2};

    private static final List<String> w = Arrays.asList(
            "3,0-3,3",
            "5,0-5,4",
            "4,5-7,5",
            "7,5-7,9"
    );

    @Override
    public String walk(String s) {
        String[] c = s.replace("[", "").replace("]", "").split(",");
        String d = c[0];
        int st = Integer.parseInt(c[1]);

        for (int i = 0; i < st; i++) {
            if (d.equals("no")) {
                if (!iw(p[0], p[1] + 1) && p[1] < xyz - 1) p[1]++;
            } else if (d.equals("ea")) {
                if (!iw(p[0] + 1, p[1]) && p[0] < abc - 1) p[0]++;
            } else if (d.equals("so")) {
                if (!iw(p[0], p[1] - 1) && p[1] > 0) p[1]--;
            } else if (d.equals("we")) {
                if (!iw(p[0] - 1, p[1]) && p[0] > 0) p[0]--;
            }
        }
        return "(" + p[0] + "," + p[1] + ")";
    }

    private boolean iw(int x, int y) {
        for (String wl : w) {
            String[] wc = wl.split("-");
            int[] s = Arrays.stream(wc[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] e = Arrays.stream(wc[1].split(",")).mapToInt(Integer::parseInt).toArray();

            if (s[0] == e[0]) { // vertical wall
                if (x == s[0] && y >= s[1] && y < e[1]) {
                    return true;
                }
            } else { // horizontal wall
                if (y == s[1] && x >= s[0] && x < e[0]) {
                    return true;
                }
            }
        }
        return false;
    }
}
