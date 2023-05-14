package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Connectable;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldConnection extends AbstractEntity implements Connectable {

    private UUID sourceId;
    private UUID destinationId;
    @Setter
    private Coordinate sourceCoordinates;
    @Setter
    private Coordinate destinationCoordinates;

    @Override
    public boolean isOnEntryCell(Coordinate coordinate) {
        return sourceCoordinates.equals(coordinate);
    }
}
