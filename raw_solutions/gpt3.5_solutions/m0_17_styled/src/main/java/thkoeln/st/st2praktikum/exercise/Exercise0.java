package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble{
    private int robotX = 11;
    private int robotY = 7;
    private int gridWidth = 12;
    private int gridHeight = 8;
    private int[][] obstacles = {{6,2,6,5},{5,5,6,5},{5,5,5,6},{5,6,12,6}};

    public String goTo(String goCommandString) {
        String[] goCommand = goCommandString.replaceAll("\\[|\\]", "").split(",");
        String direction = goCommand[0];
        int steps = Integer.parseInt(goCommand[1]);
        int newX = robotX;
        int newY = robotY;

        if (direction.equals("no")) {
            newY += steps;
        } else if (direction.equals("ea")) {
            newX += steps;
        } else if (direction.equals("so")) {
            newY -= steps;
        } else if (direction.equals("we")) {
            newX -= steps;
        }

        for (int[] obstacle : obstacles) {
            int x1 = obstacle[0];
            int y1 = obstacle[1];
            int x2 = obstacle[2];
            int y2 = obstacle[3];
            if ((newX >= x1 && newX <= x2 && newY == robotY) ||
                    (newY >= y1 && newY <= y2 && newX == robotX)) {
                newX = Math.min(Math.max(newX, 0), gridWidth);
                newY = Math.min(Math.max(newY, 0), gridHeight);
                break;
            }
        }

        robotX = newX;
        robotY = newY;
        return "(" + robotX + "," + robotY + ")";
    }
}
