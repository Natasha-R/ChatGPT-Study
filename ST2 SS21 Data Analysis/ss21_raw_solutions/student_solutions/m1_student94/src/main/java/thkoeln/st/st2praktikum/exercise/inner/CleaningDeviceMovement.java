package thkoeln.st.st2praktikum.exercise.inner;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.map.Queryable;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

@Getter
public class CleaningDeviceMovement {
//breaking down the command

    Movetype movetype;
    OrientationStand orientationStand;
    Integer stepToMove;
    Walkable space;

    public CleaningDeviceMovement(String commandString, Queryable map) {
        String movement=commandString.substring(1,commandString.length()-1);
        String[] givenCommand=movement.split(",");

        switch(givenCommand[0]){
            case "no": movetype = Movetype.MOVEMENT; orientationStand = OrientationStand.no; break;
            case "so": movetype = Movetype.MOVEMENT; orientationStand = OrientationStand.so; break;
            case "we": movetype = Movetype.MOVEMENT; orientationStand = OrientationStand.we; break;
            case "ea": movetype = Movetype.MOVEMENT; orientationStand = OrientationStand.ea; break;
            case "tr": movetype = Movetype.TRANSPORT; break;
            case "en": movetype = Movetype.INITIALISED; break;
            default: throw new IllegalArgumentException("Illegal command: "+ commandString);
        }

        if (movetype == Movetype.TRANSPORT || movetype == Movetype.INITIALISED)
            space = (Walkable) map.getSpaceByItsId(UUID.fromString(givenCommand[1]));
        else
            stepToMove = Integer.parseInt(givenCommand[1]);
    }
}
