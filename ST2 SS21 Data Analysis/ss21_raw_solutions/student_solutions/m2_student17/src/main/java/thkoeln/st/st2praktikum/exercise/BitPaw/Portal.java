package thkoeln.st.st2praktikum.exercise.BitPaw;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public class Portal
{
    private UUID toRoomID;
    private Vector2D location;
    private Vector2D pointToPosition;

    public Portal(final Vector2D location, final Vector2D pointToPosition, final UUID pointToRoomID)
    {
        setToRoomID(pointToRoomID);
        setLocation(location);
        setPointToPosition(pointToPosition);
    }

    @Override
    public String toString()
    {
        return getLocation().toString() + " -> " + getPointToPosition().toString();
    }

    public UUID getToRoomID()
    {
        return toRoomID;
    }

    private void setToRoomID(UUID roomUUID)
    {
        toRoomID = roomUUID;
    }

    public Vector2D getLocation()
    {
        return location;
    }

    private void setLocation(Vector2D position)
    {
        location = position;
    }

    public Vector2D getPointToPosition()
    {
        return pointToPosition;
    }

    private void setPointToPosition(Vector2D position)
    {
        pointToPosition = position;
    }
}