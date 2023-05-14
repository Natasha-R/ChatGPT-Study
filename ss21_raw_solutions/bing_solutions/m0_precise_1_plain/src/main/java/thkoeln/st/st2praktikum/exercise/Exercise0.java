package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 3;
    private int y = 0;
    private int width = 12;
    private int height = 8;
    private boolean[][] walls = new boolean[width][height];

    public Exercise0() {
        for (int i = 0; i <= 3; i++) walls[3][i] = true;
        for (int i = 4; i <= 7; i++) walls[i][3] = true;
        for (int i = 0; i <= 2; i++) walls[7][i] = true;
        for (int i = 1; i <= 8; i++) walls[i][4] = true;
    }

    @Override
    public String walkTo(String walkCommandString) {
        String[] parts = walkCommandString.split(",");
        String direction = parts[0].substring(1);
        int steps = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
        switch (direction) {
            case "no":
                while (steps > 0 && y + 1 < height && !walls[x][y + 1]) {
                    y++;
                    steps--;
                }
                break;
            case "ea":
                while (steps > 0 && x + 1 < width && !walls[x + 1][y]) {
                    x++;
                    steps--;
                }
                break;
            case "so":
                while (steps > 0 && y > 0 && !walls[x][y - 1]) {
                    y--;
                    steps--;
                }
                break;
            case "we":
                while (steps > 0 && x > 0 && !walls[x][y]) {
                    x--;
                    steps--;
                }
                break;
        }
        return "(" + x + "," + y + ")";
    }
}