package thkoeln.st.st2praktikum.exercise.valueObjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Barrier {

    private Coordinate barrierStartCoordinate;
    private Coordinate barrierEndCoordinate;

    public Barrier(String barrierString) {
        String[] splitString = barrierString.split("-");
        this.barrierStartCoordinate = Coordinate.parseCoordinate(splitString[0]);
        this.barrierEndCoordinate = Coordinate.parseCoordinate(splitString[1]);
    }

}
