package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Connectable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

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
    @Setter
    @ManyToOne
    private TransportTechnology technology;

    @Override
    public boolean isOnEntryCell(Coordinate coordinate) {
        return sourceCoordinates.equals(coordinate);
    }
}
