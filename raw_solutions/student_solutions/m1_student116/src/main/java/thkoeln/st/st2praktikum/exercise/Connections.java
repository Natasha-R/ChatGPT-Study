package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connections {
    private final UUID uUID;
    private final UUID sourceFieldId;
    private final int[] sourceCoordinate;
    private final UUID destinationFieldId;
    private final int[] destinationCoordinate;
    public Connections(UUID pSourceFieldId, int[] pSourceCoordinate, UUID pDestinationFieldId, int[] pDestinationCoordinate){
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

    public int[] getSourceCoordinate(){
        return sourceCoordinate;
    }

    public UUID getDestinationFieldId(){
        return destinationFieldId;
    }

    public int[] getDestinationCoordinate(){
        return destinationCoordinate;
    }
}
