package thkoeln.st.st2praktikum.exercise.domainprimitives;

import thkoeln.st.st2praktikum.exercise.space.domain.Tile;


public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;

    public Tile.Direction convertToDirection () {
        switch (this) {
            case NORTH: return Tile.Direction.NORTH;
            case WEST: return Tile.Direction.WEST;
            case SOUTH: return Tile.Direction.SOUTH;
            case EAST: return Tile.Direction.EAST;
            default: throw new RuntimeException("TaskType entspricht keiner Tile-Richtung");
        }
    }
}