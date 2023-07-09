package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connections {
    private final UUID uUID;
    private final UUID sourceFieldId;
    private final Vector2D sourceCoordinate;
    private final UUID destinationFieldId;
    private final Vector2D destinationCoordinate;
    public Connections(UUID pSourceFieldId, Vector2D pSourceCoordinate, UUID pDestinationFieldId, Vector2D pDestinationCoordinate){
        uUID=UUID.randomUUID();
        this.sourceFieldId=pSourceFieldId;
        this.sourceCoordinate=pSourceCoordinate;
        this.destinationFieldId=pDestinationFieldId;
        this.destinationCoordinate=pDestinationCoordinate;
    }

    public UUID getUUID(){
        return uUID;
    }

    public UUID getSourceFieldId(){
        return sourceFieldId;
    }

    public Vector2D getSourceCoordinate(){
        return sourceCoordinate;
    }

    public UUID getDestinationFieldId(){
        return destinationFieldId;
    }

    public Vector2D getDestinationCoordinate(){
        return destinationCoordinate;
    }
}
