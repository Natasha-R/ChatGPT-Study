package thkoeln.st.st2praktikum.exercise.field;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Connection {
    private UUID uuid = UUID.randomUUID();

    private UUID sourceFieldId;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destinationCoordinate;

    /**
     * Constructur that uses Strings instead of Coordinates
     * @param sourceFieldId where to start from as UUID
     * @param sourceCoordinateString where to start from as String will be converted to Coordinate
     * @param destinationFieldId where to go to as UUID
     * @param destinationCoordinateString where to go to as String will be converted to Coordinate
     */
    public Connection(UUID sourceFieldId, String sourceCoordinateString, UUID destinationFieldId, String destinationCoordinateString){
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourceCoordinate= Coordinate.turnStringToCoordinate(sourceCoordinateString);
        this.destinationCoordinate= Coordinate.turnStringToCoordinate(destinationCoordinateString);
    }
}
