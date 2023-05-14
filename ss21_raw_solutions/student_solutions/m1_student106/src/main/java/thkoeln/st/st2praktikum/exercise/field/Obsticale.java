package thkoeln.st.st2praktikum.exercise.field;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Obsticale {
    private UUID uuid = UUID.randomUUID();
    private UUID fieldId;
    private Coordinate start;
    private Coordinate end;

    public Obsticale(UUID fieldId, String obsticalString){
        this.fieldId = fieldId;
        setObsticalSartAndEndBasedOnString(obsticalString);
    }

    /**
     * @param obsticalString foramt "(2,3)-(10,3)"
     */
    public void setObsticalSartAndEndBasedOnString(String obsticalString){
        //Seperates into ["(2,3)","(10,3)"] Array
        String[] limits = obsticalString.split("-");

        start = Coordinate.turnStringToCoordinate(limits[0]);
        end = Coordinate.turnStringToCoordinate(limits[1]);
    }
}
