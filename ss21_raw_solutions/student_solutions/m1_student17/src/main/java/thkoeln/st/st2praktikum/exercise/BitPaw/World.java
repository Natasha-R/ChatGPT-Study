package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class World
{
    private final Map<UUID, Room> _roomList = new HashMap<>();
    private final Map<UUID, Robot> _roboterList = new HashMap<>();


    private Robot GetRobot(final UUID robotID) throws RobotNotFound
    {
        final Robot robot = _roboterList.get(robotID);

        if(robot == null)
        {
            throw new RobotNotFound(robotID);
        }

        return robot;
    }

    private Room GetRoom(final Robot targetRobot) throws RoomNotFound
    {
        for (final Room room : _roomList.values())
        {
            if(room.ContainsRobot(targetRobot.ID))
            {
                return room;
            }
        }

       throw new RoomNotFound(null);
    }

    private Room GetRoom(final UUID roomID) throws RoomNotFound
    {
        final Room room = _roomList.get(roomID);

        if(room == null)
        {
            throw new RoomNotFound(roomID);
        }

        return room;
    }

    public UUID SpawnRobot(final String name)
    {
        final Robot robot = new Robot(name);

        _roboterList.put(robot.ID, robot);

        return robot.ID;
    }

    public void AddWall(final UUID roomID, final Wall wall) throws RoomNotFound
    {
        final Room room = GetRoom(roomID);

        room.AddWall(wall);
    }

    public Position GetRobotPosition(final UUID uuid) throws RobotNotFound
    {
        final Robot robot = GetRobot(uuid);

        return robot.CurrentPosition;
    }

    public UUID AddRoom(final int width, final int height)
    {
        final Room room = new Room(width, height);

        _roomList.put(room.ID, room);

        return room.ID;
    }

    public UUID AddPortal(final UUID fromRoom, final Position location, final UUID targetRoom,final Position spawnPosition) throws RoomNotFound, InvalidPositionException
    {
        final Room roomA = GetRoom(fromRoom);
        final Room roomB = GetRoom(targetRoom);
        final boolean validRoomAPosition = roomA.IsInRoom(location);
        final boolean validRoomBPosition = roomB.IsInRoom(spawnPosition);

        if(!validRoomAPosition)
        {
            throw new InvalidPositionException("Current room", location);
        }

        if(!validRoomBPosition)
        {
            throw new InvalidPositionException("Target room", spawnPosition);
        }

        final Portal portal = new Portal(location, spawnPosition, targetRoom);

        roomA.AddPortal(portal);

        return portal.ToRoomID;
    }

    public boolean MoveRobot(final MoveCommand move) throws RobotNotFound, RoomNotFound
    {
        final Robot robot = GetRobot(move.RobotUUID);

        switch (move.Type)
        {
            case Move:
            {
                final Room currentRoom = GetRoom(robot);
                final Position nextPossiblePosition = currentRoom.GetNextPossiblePosition(move);
                //final Position oldPos = new Position(robot.CurrentPosition.X, robot.CurrentPosition.Y);

                robot.CurrentPosition.Add(nextPossiblePosition);

                //System.out.println("Moved " + oldPos +  " -> " + robot.CurrentPosition + " wanted "  + move + " got" + nextPossiblePosition);

                break;
            }
            case Transition:
            {
                final Room currentRoom = GetRoom(robot);
                Room transitionRoom;
                final Portal portal = currentRoom.GetPortalFromPosition(robot.CurrentPosition);
                final boolean canTransition = portal != null;

                if(!canTransition)
                {
                    return false; // No transition possible.
                }

                transitionRoom = GetRoom(portal.ToRoomID);

                // Swap rooms
                currentRoom.RemoveRobot(robot.ID);
                transitionRoom.AddRobot(robot);

                //System.out.println("Transition from " +robot.CurrentPosition + " to " + portal.PointToPosition);

                robot.CurrentPosition = portal.PointToPosition;

                break;
            }
            case SetPosition:
            {
                final Room targetedRoom = GetRoom(move.TargetRoomID);
                final boolean isAtPosition = targetedRoom.IsRobotAtPosition(new Position(0,0));

                if(isAtPosition)
                {
                    return false; // Robot is already at this position.
                }

                robot.CurrentPosition.Set(0,0);
                targetedRoom.AddRobot(robot);

                //System.out.println("Spawned at (0,0)");

                break;
            }
        }

        return true;
    }

    public UUID GetRoomUUIDFromRoboter(final UUID roboterUUID) throws RoomNotFound
    {
        for (final Room room : _roomList.values())
        {
            final Robot robot =  room.GetRobot(roboterUUID);

            if(robot != null)
            {
                return room.ID;
            }
        }

        return null;
    }
}
