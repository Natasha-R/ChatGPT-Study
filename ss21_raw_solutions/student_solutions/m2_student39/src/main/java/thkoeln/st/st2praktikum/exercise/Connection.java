package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID identifier;
    private UUID sourceMap;
    private UUID destinationMap;
    private Vector2D sourcePosition;
    private Vector2D destinationPosition;

    public Connection(UUID connectionUUID, UUID sourceMap, UUID destinationMap, Vector2D sourcePosition, Vector2D destinationPosition){
        this.identifier = connectionUUID;
        this.sourceMap = sourceMap;
        this.destinationMap = destinationMap;
        this.sourcePosition = sourcePosition;
        this.destinationPosition = destinationPosition;
    }

    public boolean checkSourcePosition(Vector2D vector2D) {
        return sourcePosition.equals(vector2D);
    }

    public boolean checkTargetMap(UUID destinationMap) {
        return this.destinationMap.equals(destinationMap);
    }
    public Vector2D getSourcePosition(){
        return sourcePosition;
    }

    public Vector2D getDestinationPosition() {
        return destinationPosition;
    }
}
