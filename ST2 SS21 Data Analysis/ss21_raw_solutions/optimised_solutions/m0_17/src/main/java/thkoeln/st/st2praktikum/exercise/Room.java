package thkoeln.st.st2praktikum.exercise;

class Room {
    public int width, height;
    public boolean[][] walls;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new boolean[width][height];
    }

    public void addWall(int x1, int y1, int x2, int y2) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                this.walls[x][y] = true;
            }
        }
    }
}

