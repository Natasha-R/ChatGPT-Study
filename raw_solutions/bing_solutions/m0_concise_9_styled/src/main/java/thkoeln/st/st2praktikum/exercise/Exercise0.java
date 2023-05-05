package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean[][] ws;

    public Exercise0() {
        x = 3;
        y = 0;
        w = 12;
        h = 8;
        ws = new boolean[w][h];
        for (int i = 0; i < 3; i++) {
            ws[3][i] = true;
        }
        for (int i = 4; i < 7; i++) {
            ws[i][3] = true;
        }
        for (int i = 0; i < 2; i++) {
            ws[7][i] = true;
        }
        for (int i = 1; i < 8; i++) {
            ws[i][4] = true;
        }
    }

    @Override
    public String walkTo(String s) {
        String[] p = s.split(",");
        String d = p[0].substring(1);
        int n = Integer.parseInt(p[1].substring(0, p[1].length() - 1));
        switch (d) {
            case "no":
                for (int i = 0; i < n; i++) {
                    if (y + 1 >= h || ws[x][y + 1]) break;
                    y++;
                }
                break;
            case "ea":
                for (int i = 0; i < n; i++) {
                    if (x + 1 >= w || ws[x + 1][y]) break;
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < n; i++) {
                    if (y - 1 < 0 || ws[x][y - 1]) break;
                    y--;
                }
                break;
            case "we":
                for (int i = 0; i < n; i++) {
                    if (x - 1 < 0 || ws[x - 1][y]) break;
                    x--;
                }
                break;
        }
        return "(" + x + "," + y + ")";
    }
}