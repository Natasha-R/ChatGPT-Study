package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.UUID;

public class Portal
{
    public final UUID ToRoomID;
    public final Position Location;
    public final Position PointToPosition;

    public Portal(final Position location, final Position pointToPosition, final UUID pointToRoomID)
    {
        ToRoomID = pointToRoomID;
        Location = location;
        PointToPosition = pointToPosition;
    }

    @Override
    public String toString()
    {
        return Location.toString() + " -> " + PointToPosition.toString();
    }
}
