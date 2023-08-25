package thkoeln.st.st2praktikum.exercise.utility;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.entities.Portal;
import thkoeln.st.st2praktikum.exercise.entities.Room;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.entities.TransportSystem;
import thkoeln.st.st2praktikum.exercise.exception.InvalidPositionException;
import thkoeln.st.st2praktikum.exercise.exception.TransportSystemNotFoundException;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class World
{
    private final Map<UUID, Room> roomList = new HashMap<>();
    private final Map<UUID, TransportSystem> transportSystemList = new HashMap<>();
    private static World instance;

    private World()
    {
        setInstance(this);
    }

    public void addWall(final UUID roomID, final Barrier wall) throws RoomNotFound
    {
        final Room room = getRoom(roomID);

        room.addWall(wall);
    }

    public UUID addRoom(final int width, final int height)
    {
        final Room room = new Room(width, height);

        roomList.put(room.getUUID(), room);

        return room.getUUID();
    }

    public Portal getPortal(final UUID portalUUID)
    {
        for (TransportSystem transportSystem : transportSystemList.values())
        {
            final Portal portal = transportSystem.getPortal(portalUUID);

            if(portal != null)
            {
                return portal;
            }
        }

        return null;
    }

    public UUID addPortal(final UUID transportSystemId, final UUID fromRoom, final Vector2D location, final UUID targetRoom, final Vector2D spawnPosition) throws RoomNotFound
    {
        final Room roomA = getRoom(fromRoom);
        final Room roomB = getRoom(targetRoom);
        final boolean validRoomAPosition = roomA.isInRoom(location);
        final boolean validRoomBPosition = roomB.isInRoom(spawnPosition);

        if(!validRoomAPosition)
        {
            throw new InvalidPositionException("Current room", location);
        }

        if(!validRoomBPosition)
        {
            throw new InvalidPositionException("Target room", spawnPosition);
        }

        final Portal portal = new Portal(fromRoom, location, spawnPosition, targetRoom);
        final TransportSystem transportSystem = getTransportSystem(transportSystemId);

        transportSystem.AddConnection(portal);

        return portal.getUUID();
    }

    public static World getInstance()
    {
        World world = instance;

        if(world == null)
        {
            world = new World();
        }

        return world;
    }

    private static void setInstance(World world)
    {
        instance = world;
    }

    public Portal getPortalAtPosition(final UUID roomID, final Vector2D position)
    {
        for (TransportSystem transportSystem : transportSystemList.values())
        {
            Portal portal = transportSystem.getPortalAtPosition(roomID, position);

            if(portal != null)
            {
                return portal;
            }
        }

        return null;
    }

    public Room getRoom(final TidyUpRobot targetRobot) throws RoomNotFound
    {
        for (final Room room : roomList.values())
        {
            if(room.containsRobot(targetRobot.getUUID()))
            {
                return room;
            }
        }

        throw new RoomNotFound(null);
    }

    public UUID addTransportSystem(String type)
    {
        TransportSystem transportSystem = new TransportSystem(type);

        transportSystemList.put(transportSystem.getUUID(), transportSystem);

        return transportSystem.getUUID();
    }

    public TransportSystem getTransportSystem(final UUID transportSystemUUID)
    {
        final TransportSystem transportSystem = transportSystemList.get(transportSystemUUID);

        if(transportSystem == null)
        {
            throw new TransportSystemNotFoundException(transportSystemUUID);
        }

        return transportSystem;
    }

    public Room getRoom(final UUID roomID) throws RoomNotFound
    {
        final Room room = roomList.get(roomID);

        if(room == null)
        {
            throw new RoomNotFound(roomID);
        }

        return room;
    }

    public UUID getRoomUUIDFromRoboter(final UUID roboterUUID) throws RoomNotFound
    {
        for (final Room room : roomList.values())
        {
            final TidyUpRobot robot =  room.getRobot(roboterUUID);

            if(robot != null)
            {
                return room.getUUID();
            }
        }

        return null;
    }
}
