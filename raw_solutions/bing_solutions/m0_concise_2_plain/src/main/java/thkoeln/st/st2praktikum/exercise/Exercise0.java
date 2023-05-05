package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 3;
    private int y = 0;
    private int width = 12;
    private int height = 8;
    private boolean[][] verticalWalls = new boolean[width + 1][height];
    private boolean[][] horizontalWalls = new boolean[width][height + 1];

    public Exercise0() {
        for (int i = 0; i <= 3; i++) {
            verticalWalls[3][i] = true;
        }
        for (int i = 4; i <= 7; i++) {
            horizontalWalls[i][3] = true;
        }
        for (int i = 0; i <= 2; i++) {
            verticalWalls[7][i] = true;
        }
        for (int i = 1; i <= 8; i++) {
            horizontalWalls[i][4] = true;
        }
    }

    @Override
    public String walkTo(String walkCommandString) {
        String[] parts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y + 1 >= height || horizontalWalls[x][y + 1]) break;
                    y++;
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x + 1 >= width || verticalWalls[x + 1][y]) break;
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y - 1 < 0 || horizontalWalls[x][y]) break;
                    y--;
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x - 1 < 0 || verticalWalls[x][y]) break;
                    x--;
                }
                break;
        }

        return "(" + x + "," + y + ")";
    }
}