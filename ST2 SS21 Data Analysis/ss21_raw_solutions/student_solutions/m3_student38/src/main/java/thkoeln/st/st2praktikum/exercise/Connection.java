package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.util.UUID;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
class Connection{
    private Point sourcePoint;
    private Point destPoint;
    @OneToOne
    private Field sourceField;
    @OneToOne
    private Field destField;
    private UUID transportTechnology;

    public Connection(UUID TransportTechnology, Field sourceField, Field destField, Point sourcePoint, Point destPoint){
        this.setSourceField(sourceField);
        this.setDestField(destField);
        this.setSourcePoint(sourcePoint);
        this.setDestPoint(destPoint);
        this.transportTechnology = TransportTechnology;
    }

    public Field getSourceField() {
        return sourceField;
    }

    public void setSourceField(Field sourceField) {
        this.sourceField = sourceField;
    }

    public Field getDestField() {
        return destField;
    }

    public void setDestField(Field destField) {
        this.destField = destField;
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
