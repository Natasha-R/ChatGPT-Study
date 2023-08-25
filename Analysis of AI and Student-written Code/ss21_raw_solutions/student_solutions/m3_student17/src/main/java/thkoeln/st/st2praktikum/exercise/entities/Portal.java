package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Portal
{
    @Id
    private UUID uuid;

    // From
    private UUID roomID;

    @Embedded
    private Vector2D location;

    // To
    private UUID toRoomID;

    @Embedded
    private Vector2D pointToPosition;

    public Portal( final UUID pointID, final Vector2D location, final Vector2D pointToPosition, final UUID pointToRoomID)
    {
        uuid = UUID.randomUUID();
        setRoomID(pointID);
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

    private void setRoomID(final UUID roomID)
    {
        this.roomID = roomID;
    }
    public UUID getFromRoomID()
    {
        return roomID;
    }

    public UUID getUUID()
    {
        return uuid;
    }
}