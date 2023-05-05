package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Connection {
    @Id
    private UUID id;

    private UUID sourceFieldId;
    private String sourceCoordinate;
    private UUID destinationFieldId;
    private String destinationCoordinate;

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public void setSourceFieldId(UUID sourceFieldId) {
        this.sourceFieldId = sourceFieldId;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public void setSourceCoordinate(String sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public void setDestinationFieldId(UUID destinationFieldId) {
        this.destinationFieldId = destinationFieldId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public void setDestinationCoordinate(String destinationCoordinate) {
        this.destinationCoordinate = destinationCoordinate;
    }
    public Connection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint, UUID id)
    {
        this.id = id;
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = "("+sourcePoint.getX()+","+sourcePoint.getY()+")";
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = "("+destinationPoint.getX()+","+destinationPoint.getY()+")";;

    }
}
