package thkoeln.st.st2praktikum.exercise.field;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.wall.SingleWall;

import java.util.*;

public class Field {
    private UUID uuid;
    private Coordinate start;
    private Coordinate end;
    private List<SingleWall> verticalWalls;
    private List<SingleWall> horizontalWalls;
    private Map<UUID, FieldConnection> fieldConnections;

    public Field(UUID uuid, Coordinate start, Coordinate end) {
        this.uuid = uuid;
        this.start = start;
        this.end = end;
        this.verticalWalls = new ArrayList<>();
        this.horizontalWalls = new ArrayList<>();
        this.fieldConnections = new HashMap<>();
    }

    public Field(UUID uuid, Coordinate end) {
        this(uuid, new Coordinate(0, 0), end);
    }

    public Field(UUID uuid, Integer x, Integer y) {
        this(uuid, new Coordinate(x, y));
    }

    public UUID getUuid() {
        return uuid;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public List<SingleWall> getHorizontalWalls() {
        return horizontalWalls;
    }

    public List<SingleWall> getVerticalWalls() {
        return verticalWalls;
    }

    public Map<UUID, FieldConnection> getFieldConnections() {
        return fieldConnections;
    }
}
