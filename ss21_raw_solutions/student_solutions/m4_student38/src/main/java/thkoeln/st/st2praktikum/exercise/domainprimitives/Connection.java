package thkoeln.st.st2praktikum.exercise.domainprimitives;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Connection{
    private Point sourcePoint;
    private Point destPoint;
    private UUID sourceFieldID;
    private UUID destFieldID;
    private UUID transportTechnology;

    public Connection(UUID TransportTechnology, UUID sourceFieldID, UUID destFieldID, Point sourcePoint, Point destPoint){
        this.setSourceField(sourceFieldID);
        this.setDestField(destFieldID);
        this.setSourcePoint(sourcePoint);
        this.setDestPoint(destPoint);
        this.transportTechnology = TransportTechnology;
    }

    public UUID getSourceField() {
        return sourceFieldID;
    }

    public void setSourceField(UUID sourceField) {
        this.sourceFieldID = sourceField;
    }

    public UUID getDestField() {
        return destFieldID;
    }

    public void setDestField(UUID destField) {
        this.destFieldID = destField;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public void setSourcePoint(Point sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    public Point getDestPoint() {
        return destPoint;
    }

    public void setDestPoint(Point destPoint) {
        this.destPoint = destPoint;
    }
}
