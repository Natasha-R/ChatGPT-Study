package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 3;
    private int y = 0;
    private int width = 12;
    private int height = 8;
    private boolean[][] walls = new boolean[width][height];

    public Exercise0() {
        // Initialize walls
        for (int i = 0; i <= 3; i++) {
            walls[3][i] = true;
        }
        for (int i = 4; i <= 7; i++) {
            walls[i][3] = true;
        }
        for (int i = 0; i <= 2; i++) {
            walls[7][i] = true;
        }
        for (int i = 1; i <= 8; i++) {
            walls[i][4] = true;
        }
    }

    public String walkTo(String walkCommandString) {
        String[] parts = walkCommandString.split(",");
        String direction = parts[0].substring(1);
        int steps = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y + 1 >= height || walls[x][y + 1]) break;
                    y++;
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x + 1 >= width || walls[x + 1][y]) break;
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y - 1 < 0 || walls[x][y - 1]) break;
                    y--;
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x - 1 < 0 || walls[x - 1][y]) break;
                    x--;
                }
                break;
        }

        return "(" + x + "," + y + ")";
    }
}