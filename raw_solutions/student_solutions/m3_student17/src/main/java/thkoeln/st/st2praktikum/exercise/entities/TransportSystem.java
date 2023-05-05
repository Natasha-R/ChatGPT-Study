package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.sound.sampled.Port;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
public class TransportSystem
{
    @Id
    private UUID uuid;
    private String transportSystemType;

    @Embedded
    private Map<UUID, Portal> connectionList = new HashMap();

    public TransportSystem(final String transportSystemType)
    {
        uuid = UUID.randomUUID();
        this.transportSystemType = transportSystemType;
    }

    public Portal getPortalAtPosition(final UUID roomID, final Vector2D position)
    {
        for (final Portal portal : connectionList.values())
        {
            boolean isInRoom = portal.getFromRoomID().equals(roomID);
            boolean isAtSamePosition =  portal.getLocation().equals(position);

            if(isInRoom && isAtSamePosition)
            {
                return portal;
            }
        }

        return null;
    }

    public UUID getUUID()
    {
        return uuid;
    }

    public String getTransportSystemType()
    {
        return transportSystemType;
    }

    public void AddConnection(final Portal portal)
    {
        connectionList.put(portal.getUUID(), portal);
    }

    public Portal getPortal(final UUID uuid)
    {
        return connectionList.get(uuid);
    }
}