package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.Coordinates;

@AllArgsConstructor
public class Connection{
    @Getter
    private final Coordinates sourceRoomCoordinates;
    @Getter
    private final Coordinates destinationRoomCoordinates;
}
