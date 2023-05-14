package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;


public class Tile {

    public enum Direction {
        WEST, EAST, SOUTH, NORTH
    }

    @Getter
    @Setter
    private Tile westNeighbor, eastNeighbor, southNeighbor, northNeighbor;

    @Getter
    @Setter
    private Blocking occupier = null;

    public Tile getNeighbor (Direction direction) {
        switch (direction) {
            case WEST: return westNeighbor;
            case EAST: return eastNeighbor;
            case SOUTH: return southNeighbor;
            case NORTH: return northNeighbor;
        }
        return null;
    }

    public void setNeighbor(Direction direction, Tile tile) {
        switch (direction) {
            case WEST: westNeighbor = tile; break;
            case EAST: eastNeighbor = tile; break;
            case SOUTH: southNeighbor = tile; break;
            case NORTH: northNeighbor = tile; break;
        }
    }

    public Boolean isOccupied() {
        return occupier != null;
    }
    public void removeOccupier() {
        occupier = null;
    }
}
