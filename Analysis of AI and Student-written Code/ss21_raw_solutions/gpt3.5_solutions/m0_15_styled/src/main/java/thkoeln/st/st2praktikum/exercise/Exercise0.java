package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private int x = 11;
    private int y = 7;

    private final int deckWidth = 12;
    private final int deckHeight = 8;

    private final int[][] obstacles = {{6,2,6,5}, {5,5,6,5}, {5,5,5,6}, {5,6,12,6}};

    public String goTo(String goCommandString) {
        String[] command = goCommandString.replaceAll("[\\[\\]]", "").split(",");

        String direction = command[0];
        int steps = Integer.parseInt(command[1]);

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y+1 >= deckHeight) break;
                    boolean isObstacle = checkObstacles(x, y+1);
                    if (isObstacle) break;
                    y++;
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x+1 >= deckWidth) break;
                    boolean isObstacle = checkObstacles(x+1, y);
                    if (isObstacle) break;
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y-1 < 0) break;
                    boolean isObstacle = checkObstacles(x, y-1);
                    if (isObstacle) break;
                    y--;
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x-1 < 0) break;
                    boolean isObstacle = checkObstacles(x-1, y);
                    if (isObstacle) break;
                    x--;
                }
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }

        return "(" + x + "," + y + ")";
    }

    private boolean checkObstacles(int x2, int y2) {
        for (int[] obstacle : obstacles) {
            int x1 = obstacle[0];
            int y1 = obstacle[1];
            int x3 = obstacle[2];
            int y3 = obstacle[3];

            if (x2 >= x1 && x2 <= x3 && y2 >= y1 && y2 <= y3) {
                return true;
            }
        }

        return false;
    }
}
