package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldConnection extends AbstractEntity implements Connectable {

    @ManyToOne
    private Field source;
    @ManyToOne
    private Field destination;
    @Setter
    @Embedded
    private Coordinate sourceCoordinates;
    @Setter
    @Embedded
    private Coordinate destinationCoordinates;

    @Override
    public boolean isOnEntryCell(Coordinate coordinate) {
        return sourceCoordinates.equals(coordinate);
    }
}
