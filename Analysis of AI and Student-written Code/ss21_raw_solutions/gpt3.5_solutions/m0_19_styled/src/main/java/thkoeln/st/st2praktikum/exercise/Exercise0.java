package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble{
    private int droidX;
    private int droidY;
    private int gridWidth = 12;
    private int gridHeight = 8;
    private boolean[][] obstacles;

    public Exercise0() {
        droidX = 11;
        droidY = 7;
        obstacles = new boolean[gridWidth][gridHeight];
        // Set up the obstacles
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                obstacles[x][y] = false;
            }
        }
        obstacles[6][2] = true;
        obstacles[6][3] = true;
        obstacles[6][4] = true;
        obstacles[6][5] = true;
        obstacles[5][5] = true;
        obstacles[5][6] = true;
        obstacles[7][6] = true;
        obstacles[8][6] = true;
        obstacles[9][6] = true;
        obstacles[10][6] = true;
        obstacles[11][6] = true;
    }

    public String goTo(String goCommandString) {
        String[] parts = goCommandString.split(",");
        String direction = parts[0].substring(1);
        int steps = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
        int newX = droidX;
        int newY = droidY;
        switch (direction) {
            case "no":
                newY += steps;
                break;
            case "ea":
                newX += steps;
                break;
            case "so":
                newY -= steps;
                break;
            case "we":
                newX -= steps;
                break;
            default:
                break;
        }
        // Check for obstacles and spaceship deck boundary
        if (newX < 0) {
            newX = 0;
        }
        if (newX >= gridWidth) {
            newX = gridWidth - 1;
        }
        if (newY < 0) {
            newY = 0;
        }
        if (newY >= gridHeight) {
            newY = gridHeight - 1;
        }
        if (obstacles[newX][newY]) {
            // Check for obstacle in the way
            if (direction.equals("no") || direction.equals("so")) {
                // Check if obstacle is above or below the droid
                int maxSteps = Math.abs(newY - droidY);
                if (newY > droidY) {
                    newY = droidY + maxSteps;
                } else {
                    newY = droidY - maxSteps;
                }
            } else {
                // Obstacle is to the east or west of the droid
                int maxSteps = Math.abs(newX - droidX);
                if (newX > droidX) {
                    newX = droidX + maxSteps;
                } else {
                    newX = droidX - maxSteps;
                }
            }
        }
        // Update the droid's position
        droidX = newX;
        droidY = newY;
        return "(" + (droidX+1) + "," + (droidY+1) + ")";
    }
}

