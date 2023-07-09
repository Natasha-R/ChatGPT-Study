package thkoeln.st.st2praktikum.exercise;

class Robot {
    private int x, y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void walk(Command command) {
        command.execute(this);
    }

    public String getPosition() {
        return "(" + this.x + "," + this.y + ")";
    }

    public void moveNorth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (!Wall.isWall(x, y+1)) y++;
        }
    }

    public void moveEast(int steps) {
        for (int i = 0; i < steps; i++) {
            if (!Wall.isWall(x+1, y)) x++;
        }
    }

    public void moveSouth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (!Wall.isWall(x, y-1)) y--;
        }
    }

    public void moveWest(int steps) {
        for (int i = 0; i < steps; i++) {
            if (!Wall.isWall(x-1, y)) x--;
        }
    }
}

