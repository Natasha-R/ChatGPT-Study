package thkoeln.st.st2praktikum.exercise;

public class Field {
    Wall[] walls;
    int numWallsInserted = 0;
    int sizeX;
    int sizeY;

    public Field(int sizeX, int sizeY, int countWalls) {
        walls = new Wall[countWalls];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void addWall(Wall wall) {
        walls[numWallsInserted] = wall;
        numWallsInserted++;
    }
    public Boolean isTargetWithinFieldLimits(int targetX, int targetY) {
        if (targetX > this.sizeX || targetX < 0 || targetY > this.sizeY || targetY < 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean isStepClear(int stepStartX, int stepStartY, int stepEndX, int stepEndY) {
        if (!this.isTargetWithinFieldLimits(stepEndX, stepEndY)) { return false; }
        for (int i=0; i<walls.length; i++) {
            if (walls[i].isStepBlocked(stepStartX, stepStartY, stepEndX, stepEndY)) {
                return false;
            }
        }
        return true;
    }
}
