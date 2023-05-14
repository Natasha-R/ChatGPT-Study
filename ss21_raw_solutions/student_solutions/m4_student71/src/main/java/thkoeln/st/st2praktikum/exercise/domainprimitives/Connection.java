package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
public class Connection extends UUIDBase {

    @OneToOne
    @Getter @Setter
    private Field sourceField = null;

    @Getter @Setter
    @Embedded
    private Coordinate sourceCoordinates = null;

    @OneToOne
    @Getter @Setter
    private Field destinationField = null;

    @Getter @Setter
    @Embedded
    private Coordinate destinationCoordinates = null;

    public Connection(Field sourceField, Field destinationField, Coordinate sourceCoordinates, Coordinate destinationCoordinates) {
        super();
        this.destinationField = destinationField;
        this.destinationCoordinates = destinationCoordinates;
        this.sourceField = sourceField;
        this.sourceCoordinates = sourceCoordinates;
    }




}
