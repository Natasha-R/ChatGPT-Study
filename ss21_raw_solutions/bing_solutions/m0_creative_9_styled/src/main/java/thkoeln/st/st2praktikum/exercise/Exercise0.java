package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private int x;
    private int y;
    private int width;
    private int height;
    private int[][] walls;

    public Exercise0() {
        x = 3;
        y = 0;
        width = 12;
        height = 8;
        walls = new int[][]{{3,0,3,3}, {4,3,7,3}, {7,0,7,2}, {1,4,8,4}};
    }

    @Override
    public String walkTo(String walkCommandString) {
        walkCommandString = walkCommandString.replace("[", "").replace("]", "");
        String[] parts = walkCommandString.split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        if (direction.equals("no")) {
            for (int i = 0; i < steps; i++) {
                boolean blocked = false;
                if (y == height - 1) {
                    blocked = true;
                } else {
                    for (int j = 0; j < walls.length; j++) {
                        if (walls[j][0] == walls[j][2] && walls[j][0] == x && walls[j][1] <= y && walls[j][3] > y) {
                            blocked = true;
                            break;
                        }
                    }
                }
                if (!blocked) {
                    y++;
                } else {
                    break;
                }
            }
        } else if (direction.equals("ea")) {
            for (int i = 0; i < steps; i++) {
                boolean blocked = false;
                if (x == width - 1) {
                    blocked = true;
                } else {
                    for (int j = 0; j < walls.length; j++) {
                        if (walls[j][1] == walls[j][3] && walls[j][1] == y && walls[j][0] <= x && walls[j][2] > x) {
                            blocked = true;
                            break;
                        }
                    }
                }
                if (!blocked) {
                    x++;
                } else {
                    break;
                }
            }
        } else if (direction.equals("so")) {
            for (int i = 0; i < steps; i++) {
                boolean blocked = false;
                if (y == 0) {
                    blocked = true;
                } else {
                    for (int j = 0; j < walls.length; j++) {
                        if (walls[j][0] == walls[j][2] && walls[j][0] == x && walls[j][1] >= y && walls[j][1] < y) {
                            blocked = true;
                            break;
                        }
                    }
                }
                if (!blocked) {
                    y--;
                } else {
                    break;
                }
            }
        } else if (direction.equals("we")) {
            for (int i = 0; i < steps; i++) {
                boolean blocked = false;
                if (x == 0) {
                    blocked = true;
                } else {
                    for (int j = 0; j < walls.length; j++) {
                        if (walls[j][1] == walls[j][3] && walls[j][1] == y && walls[j][0] >= x && walls[j][0] < x) {
                            blocked = true;
                            break;
                        }
                    }
                }
                if (!blocked) {
                    x--;
                } else {
                    break;
                }
            }
        } else {
            return null;
        }

        return "(" + x + "," + y + ")";
    }
}