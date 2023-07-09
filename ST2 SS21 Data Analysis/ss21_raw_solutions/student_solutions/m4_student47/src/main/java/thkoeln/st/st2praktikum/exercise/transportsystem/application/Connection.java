package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;


@Getter
@Setter
//@Entity
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    // @Id
    // private UUID connectionID;

    @OneToOne
    private Field sourceField;
    @OneToOne
    private Field destinationField;
    @Embedded
    private Point sourceCoordinates;
    @Embedded
    private Point destinationCoordinates;

    public Connection(Field sourceFieldId, Point sourceCoordinate, Field destinationFieldId, Point destinationCoordinate) {
        //this.connectionID = UUID.randomUUID();
        this.sourceField = sourceFieldId;
        this.destinationField = destinationFieldId;
        this.sourceCoordinates = sourceCoordinate;
        this.destinationCoordinates = destinationCoordinate;
    }



}
