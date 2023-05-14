package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private int x;
    private int y;
    private String[][] movGrid;

    public Exercise0() {
        movGrid = new String[11][8];
        x = 4;
        y = 0;
        initMovGrid();
    }

    private void initMovGrid() {
        for (int i = 0; i < 6; i++) {
            movGrid[0][i] = "ea";
            movGrid[1][i] = "we";
            if (i != 0) {
                movGrid[6][i] = "ea";
                movGrid[7][i] = "we";
            }
        }
        for (int i = 1; i < 7; i++) {
            if (i != 4) {
                movGrid[i][5] += " no";
                movGrid[i][6] += " so";
            }
        }
    }

    @Override
    public String go(String goCommandString) {
        String[] commands = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        int distance = Integer.parseInt(commands[1]);
        switch (commands[0]) {
            case "no":
                moveNo(distance);
                break;
            case "so":
                moveSo(distance);
                break;
            case "we":
                moveWe(distance);
                break;
            case "ea":
                moveEa(distance);
        }
        return ("(" + x + "," + y + ")");
    }

    private void moveNo(int distance) {
        for (int i = 0; i < distance; i++) {
            if (movGrid[x][y] != null) {
                String[] moves = movGrid[x][y].split(" ");
                if (y + 1 == 8 || moves[0].equals("no") || (moves.length > 1 && moves[1].equals("no"))) {
                    break;
                } else {
                    y++;
                }
            } else if (y + 1 != 8) {
                y++;
            } else {
                break;
            }
        }
    }

    private void moveSo(int distance) {
        for (int i = 0; i < distance; i++) {
            if (movGrid[x][y] != null) {
                String[] moves = movGrid[x][y].split(" ");
                if (y - 1 == -1 || moves[0].equals("so") || (moves.length > 1 && moves[1].equals("so"))) {
                    break;
                } else{
                    y--;
                }
            } else if (y - 1 != -1) {
                y--;
            } else {
                break;
            }
        }
    }

    private void moveWe(int distance) {
        for (int i = 0; i < distance; i++) {
            if (movGrid[x][y] != null) {
                String[] moves = movGrid[x][y].split(" ");
                if (x - 1 == -1 || moves[0].equals("we") || (moves.length > 1 && moves[1].equals("we"))) {
                    break;
                } else {
                    x--;
                }
            } else if (x - 1 != -1) {
                x--;
            } else {
                break;
            }
        }
    }

    private void moveEa(int distance) {
        for (int i = 0; i < distance; i++) {
            if (movGrid[x][y] != null) {
                String[] moves = movGrid[x][y].split(" ");
                if (x + 1 == 11 || moves[0].equals("ea") || (moves.length > 1 && moves[1].equals("ea"))) {
                    break;
                } else {
                    x++;
                }
            } else if (x + 1 != 11) {
                x++;
            } else {
                break;
            }
        }
    }
}
