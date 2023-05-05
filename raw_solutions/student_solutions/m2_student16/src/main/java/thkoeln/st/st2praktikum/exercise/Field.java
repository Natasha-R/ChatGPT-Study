package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Field {
    private final UUID fieldId;
    private final Integer height;
    private final Integer width;
    private List<Wall> walls = new ArrayList<>();
    private HashMap<UUID,Connection> connections = new HashMap<>();

    public Field(Integer height, Integer width){
        this.height = height;
        this.width = width;
        this.fieldId = UUID.randomUUID();
    }

    public void addConnection(Connection connection){
        connections.put(connection.getConnectionID(), connection);
    }

    public boolean coordinateIsNotOutOfBounds(Coordinate coordinate){
        Integer coordinateX = coordinate.getX();
        Integer coordinateY = coordinate.getY();
        return coordinateX <= width && coordinateY <= height && coordinateX >= 0 && coordinateY >= 0;
    }

    public void addWall(Wall wall){ walls.add(wall); }

    public UUID getFieldId(){
        return fieldId;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public HashMap<UUID, Connection> getConnections() {
        return connections;
    }
}
