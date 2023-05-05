package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    // values for the spaceship deck size and obstacle locations
    private final int WIDTH = 12;
    private final int HEIGHT = 8;
    private final int[][] OBSTACLES = {{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};

    // initial position for the maintenance droid
    private int x = 11;
    private int y = 7;

    @Override
    public String goTo(String goCommandString) {
        // parse the goCommandString to get the direction and number of steps
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));

        // update the position of the maintenance droid based on the given direction and number of steps
        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y + 1 >= HEIGHT) break;
                    boolean obstacleDetected = false;
                    for (int[] obstacle : OBSTACLES) {
                        if (obstacle[0] <= x && x <= obstacle[2] && obstacle[1] == y + 1) {
                            obstacleDetected = true;
                            break;
                        }
                    }
                    if (obstacleDetected) break;
                    y++;
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x + 1 >= WIDTH) break;
                    boolean obstacleDetected = false;
                    for (int[] obstacle : OBSTACLES) {
                        if (obstacle[1] <= y && y <= obstacle[3] && obstacle[0] == x + 1) {
                            obstacleDetected = true;
                            break;
                        }
                    }
                    if (obstacleDetected) break;
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y - 1 < 0) break;
                    boolean obstacleDetected = false;
                    for (int[] obstacle : OBSTACLES) {
                        if (obstacle[0] <= x && x <= obstacle[2] && obstacle[3] == y - 1) {
                            obstacleDetected = true;
                            break;
                        }
                    }
                    if (obstacleDetected) break;
                    y--;
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x - 1 < 0) break;
                    boolean obstacleDetected = false;
                    for (int[] obstacle : OBSTACLES) {
                        if (obstacle[1] <= y && y <= obstacle[3] && obstacle[2] == x - 1) {
                            obstacleDetected = true;
                            break;
                        }
                    }
                    if (obstacleDetected) break;
                    x--;
                }
                break;
        }

        // return the new position of the maintenance droid
        return "(" + x + "," + y + ")";
    }
}
