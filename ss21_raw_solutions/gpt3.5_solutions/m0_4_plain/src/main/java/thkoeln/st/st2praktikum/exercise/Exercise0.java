package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private int[][] obstacles;
    private int[][] spaceshipDeck;
    private int[] currentPosition;

    public Exercise0() {
        obstacles = new int[][]{{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};
        spaceshipDeck = new int[][]{{0, 0, 12, 8}};
        currentPosition = new int[]{11, 7};
    }

    @Override
    public String goTo(String goCommandString) {
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));
        String direction = goCommandString.substring(1, 3);
        int[] newPosition = new int[]{currentPosition[0], currentPosition[1]};
        switch (direction) {
            case "no": // move north
                for (int i = 0; i < steps; i++) {
                    newPosition[1]++;
                    if (isCollision(newPosition)) {
                        newPosition[1]--;
                        break;
                    }
                }
                break;
            case "ea": // move east
                for (int i = 0; i < steps; i++) {
                    newPosition[0]++;
                    if (isCollision(newPosition)) {
                        newPosition[0]--;
                        break;
                    }
                }
                break;
            case "so": // move south
                for (int i = 0; i < steps; i++) {
                    newPosition[1]--;
                    if (isCollision(newPosition)) {
                        newPosition[1]++;
                        break;
                    }
                }
                break;
            case "we": // move west
                for (int i = 0; i < steps; i++) {
                    newPosition[0]--;
                    if (isCollision(newPosition)) {
                        newPosition[0]++;
                        break;
                    }
                }
                break;
        }
        currentPosition = newPosition;
        return "(" + currentPosition[0] + "," + currentPosition[1] + ")";
    }

    private boolean isCollision(int[] position) {
        for (int[] obstacle : obstacles) {
            if ((position[0] >= obstacle[0] && position[0] <= obstacle[2])
                    && (position[1] >= obstacle[1] && position[1] <= obstacle[3])) {
                return true;
            }
        }
        for (int[] deck : spaceshipDeck) {
            if ((position[0] >= deck[0] && position[0] <= deck[2])
                    && (position[1] >= deck[1] && position[1] <= deck[3])) {
                return false;
            }
        }
        return true;
    }
}