package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Field {

    // defining default values for the class
    UUID SpaceID;
    boolean borderTop = false;
    boolean borderRight = false;
    boolean borderBottom = false;
    boolean borderLeft = false;
    boolean isOccupied = false;
    boolean isPortal = false;
    UUID portalID = new UUID(0, 0);
    UUID destination = new UUID(0, 0);

    public Field (UUID SpaceID) {
        // stores the incoming value for the new object
        this.SpaceID = SpaceID;
    }

}
