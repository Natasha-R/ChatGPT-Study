package thkoeln.st.st2praktikum.exercise.field;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.DataStorage;
import thkoeln.st.st2praktikum.exercise.Point;

import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
public class Connection {
    @Id
    private UUID uuid = UUID.randomUUID();

    private UUID sourceFieldId;
    private Point sourcePoint;
    private UUID destinationFieldId;
    private Point destinationPoint;

    /**
     * Constructur that uses Strings instead of Coordinates
     *
     * @param sourceFieldId          where to start from as UUID
     * @param sourcePointString      where to start from as String will be converted to Coordinate
     * @param destinationFieldId     where to go to as UUID
     * @param destinationPointString where to go to as String will be converted to Coordinate
     */
    public Connection(UUID sourceFieldId, String sourcePointString, UUID destinationFieldId, String destinationPointString) throws RuntimeException {
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourcePoint = new Point(sourcePointString);
        this.destinationPoint = new Point(destinationPointString);
        DataStorage.getFieldMap().get(sourceFieldId).ceckPointToBeWithinBoarders(sourcePoint);
        DataStorage.getFieldMap().get(sourceFieldId).ceckPointToBeWithinBoarders(destinationPoint);
    }

    public Connection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) throws RuntimeException {
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        DataStorage.getFieldMap().get(sourceFieldId).ceckPointToBeWithinBoarders(sourcePoint);
        DataStorage.getFieldMap().get(sourceFieldId).ceckPointToBeWithinBoarders(destinationPoint);
    }
}
