package thkoeln.st.st2praktikum.exercise.BitPaw;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class World
{
    private final Map<UUID, Room> roomList = new HashMap<>();
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

    public UUID addPortal(final UUID fromRoom, final Vector2D location, final UUID targetRoom, final Vector2D spawnPosition) throws RoomNotFound
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

        final Portal portal = new Portal(location, spawnPosition, targetRoom);

        roomA.addPortal(portal);

        return portal.getToRoomID();
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

    public Room getRoom(final Robot targetRobot) throws RoomNotFound
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
            final Robot robot =  room.getRobot(roboterUUID);

            if(robot != null)
            {
                return room.getUUID();
            }
        }

        return null;
    }
}
