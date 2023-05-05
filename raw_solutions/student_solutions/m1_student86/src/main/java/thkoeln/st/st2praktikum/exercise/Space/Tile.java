package thkoeln.st.st2praktikum.exercise.Space;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Obstacle.Obstacle;


public class Tile {

    public enum Direction {
        WEST, EAST, SOUTH, NORTH
    }

    @Getter
    @Setter
    private Tile westNeighbor, eastNeighbor, southNeighbor, northNeighbor;

    @Getter
    @Setter
    private Obstacle obstacle = null;

    public Tile getNeighbor (Direction direction) {
        switch (direction) {
            case WEST: return westNeighbor;
            case EAST: return eastNeighbor;
            case SOUTH: return southNeighbor;
            case NORTH: return northNeighbor;
            default: return null;
        }
    }

    public Boolean isOccupied() {
        return obstacle != null;
    }

    public void removeObstacle () {
        obstacle = null;
    }
}
