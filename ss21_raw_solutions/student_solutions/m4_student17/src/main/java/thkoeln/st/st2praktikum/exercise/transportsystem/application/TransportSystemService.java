package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.repositories.PortalRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Portal;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.utility.World;


import java.util.UUID;


@Service
public class TransportSystemService
{
    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private PortalRepository portalRepository;

    private final World world = World.getInstance();

    private World getWorld()
    {
        return world;
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system)
    {
        final UUID uuid = getWorld().addTransportSystem(system);

        final TransportSystem transportSystem = getWorld().getTransportSystem(uuid);

        transportSystemRepository.save(transportSystem);

        return uuid;
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId,
                              UUID sourceRoomId,
                              Vector2D sourceVector2D,
                              UUID destinationRoomId,
                              Vector2D destinationVector2D)
    {
        try
        {
            final UUID uuid = getWorld().addPortal(transportSystemId, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);

            final Portal portal = getWorld().getPortal(uuid);

            portalRepository.save(portal);

            return uuid;
        }
        catch (RoomNotFound exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
}
