package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Field {
    private final UUID fieldId;
    private final Integer height;
    private final Integer width;
    private List<Wall> walls = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public Field(Integer height, Integer width){
        this.height = height;
        this.width = width;
        this.fieldId = UUID.randomUUID();
    }

    public void addConnection(Connection connection){
        connections.add(connection);
    }

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

    public List<Connection> getConnections() {
        return connections;
    }
}
