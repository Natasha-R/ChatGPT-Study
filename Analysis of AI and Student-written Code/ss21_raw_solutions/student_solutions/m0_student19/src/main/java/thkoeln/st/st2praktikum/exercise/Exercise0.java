package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private final Cell[][] cellGrid;
    private int cleaningDeviceX = 1;
    private int cleaningDeviceY = 6;
    private final int xDimension = 11;
    private final int yDimension = 8;

    public Exercise0() {
        cellGrid = new Cell[xDimension][yDimension];

        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        setBorder(0, 6, 1, 6);
        setBorder(3, 6, 3, 7);
        setBorder(1, 5, 8, 5);
        setBorder(9, 1, 9, 4);
    }

    private void setBorder(int startX, int startY, int endX, int endY) {
        if ((endX - startX) != 0) {

            for (int i = startX; i <= endX; i++) {
                cellGrid[i][startY].setBorderSouth(true);
                cellGrid[i][startY - 1].setBorderNorth(true);
            }

        } else if ((endY - startY) != 0) {

            for (int i = startY; i <= endY; i++) {
                cellGrid[startX][i].setBorderWest(true);
                cellGrid[startX - 1][i].setBorderEast(true);
            }

        }
    }

    @Override
    public String goTo(String goCommandString) {
        String[] commands = goCommandString.split(",");
        String direction = commands[0].substring(1);
        int count = Integer.parseInt(commands[1].substring(0, commands[1].length() - 1));

        switch (direction) {
            case "no":
                this.moveNorth(count);
                break;
            case "ea":
                this.moveEast(count);
                break;
            case "so":
                this.moveSouth(count);
                break;
            case "we":
                this.moveWest(count);
                break;
        }

        return "(" + this.cleaningDeviceX + "," + this.cleaningDeviceY + ")";
    }

    private void moveNorth(int count) {
        for (int i = count; i > 0; i--) {
            if (!this.cellGrid[this.cleaningDeviceX][this.cleaningDeviceY].isBorderNorth() &&
                    this.cleaningDeviceY + 1 < this.yDimension) {
                this.cleaningDeviceY++;
            } else return;
        }
    }

    private void moveEast(int count) {
        for (int i = count; i > 0; i--) {
            if (!this.cellGrid[this.cleaningDeviceX][this.cleaningDeviceY].isBorderEast() &&
                    this.cleaningDeviceX + 1 < this.xDimension) {
                this.cleaningDeviceX++;
            } else return;
        }
    }

    private void moveSouth(int count) {
        for (int i = count; i > 0; i--) {
            if (!this.cellGrid[this.cleaningDeviceX][this.cleaningDeviceY].isBorderSouth() &&
                    this.cleaningDeviceY - 1 >= 0) {
                this.cleaningDeviceY--;
            } else return;
        }
    }

    private void moveWest(int count) {
        for (int i = count; i > 0; i--) {
            if (!this.cellGrid[this.cleaningDeviceX][this.cleaningDeviceY].isBorderWest() &&
                    this.cleaningDeviceX - 1 >= 0) {
                this.cleaningDeviceX--;
            } else return;
        }
    }
}
