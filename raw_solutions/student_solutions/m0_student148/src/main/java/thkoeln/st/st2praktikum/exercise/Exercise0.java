package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    int x = 1;
    int y = 6;

    @Override
    public String goTo(String goCommandString) {
        String commandString = goCommandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String moveArray[] = commandString.split(",");
        String moveDirection = moveArray[0];
        int steps = Integer.parseInt(moveArray[1]);
        movement(moveDirection, steps);
        return "(" + x + "," + y + ")";
    }

    public void movement(String moveDirection, int steps) {
        switch (moveDirection) {
            case "no":
                y = checkNorth(y, steps);
                break;
            case "ea":
                x = checkEast(x, steps);
                break;
            case "so":
                y = checkSouth(y, steps);
                break;
            case "we":
                x = checkWest(x, steps);
                break;
            default:
                throw new IllegalArgumentException("Move not allowed!");
        }
    }

    public int checkNorth(int yMove, int steps) {
        int[] illegalXs1 = new int[]{0, 1};
        int[] illegalYs1 = new int[]{6};
        int[] illegalXs2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] illegalYs2 = new int[]{5};
        for (int i = 0; i < steps; i++) {
            if (yMove == 7) break;
            else if ((contains(illegalYs1, yMove + 1) && contains(illegalXs1, x)) || (contains(illegalYs2, yMove + 1) && contains(illegalXs2, x)))
                break;
            else yMove += 1;
        }
        return yMove;
    }

    public int checkEast(int xMove, int steps) {
        int[] illegalXs1 = new int[]{9};
        int[] illegalYs1 = new int[]{1, 2, 3, 4};
        int[] illegalXs2 = new int[]{3};
        int[] illegalYs2 = new int[]{6, 7};

        for (int i = 0; i < steps; i++) {
            if (xMove == 10) break;
            else if ((contains(illegalYs1, y) && contains(illegalXs1, xMove + 1)) || (contains(illegalYs2, y) && contains(illegalXs2, xMove + 1)))
                break;
            else xMove += 1;
        }
        return xMove;
    }

    public int checkSouth(int yMove, int steps) {
        int[] illegalXs1 = new int[]{0, 1};
        int[] illegalYs1 = new int[]{5};
        int[] illegalXs2 = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] illegalYs2 = new int[]{4};
        for (int i = 0; i < steps; i++) {
            if (yMove == 0) break;
            else if ((contains(illegalYs1, yMove - 1) && contains(illegalXs1, x)) || (contains(illegalYs2, yMove - 1) && contains(illegalXs2, x)))
                break;
            else yMove -= 1;
        }
        return yMove;
    }

    public int checkWest(int xMove, int steps) {
        int[] illegalXs1 = new int[]{8};
        int[] illegalYs1 = new int[]{1, 2, 3, 4};
        int[] illegalXs2 = new int[]{2};
        int[] illegalYs2 = new int[]{6, 7};
        for (int i = 0; i < steps; i++) {
            if (xMove == 0) break;
            else if ((contains(illegalYs1, y) && contains(illegalXs1, xMove - 1)) || (contains(illegalYs2, y) && contains(illegalXs2, xMove - 1)))
                break;
            else xMove -= 1;
        }
        return xMove;
    }

    public boolean contains(int[] list, int move) {
        boolean isInList = false;
        for (int j : list) {
            if (j == move) {
                isInList = true;
                break;
            }
        }
        return isInList;
    }
}
