package thkoeln.st.st2praktikum.exercise.BitPaw;

import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public class Robot
{
    private UUID id;
    private String name;
    private Vector2D currentPosition;
    private Room currentRoom;

    public Robot(final String name)
    {
        setUUID(UUID.randomUUID());
        setCurrentPosition(new Vector2D(0,0));
        setName(name);
    }


    private  void moveRobot(final Order moveCommand)
    {
        final Vector2D nextPossiblePosition = currentRoom.getNextPossiblePosition(this, moveCommand);
        //final Position oldPos = new Position(robot.CurrentPosition.X, robot.CurrentPosition.Y);

        //Vector2D position = getCurrentPosition();
        //position.add(nextPossiblePosition);
        setCurrentPosition(nextPossiblePosition);

        //System.out.println("Moved " + oldPos +  " -> " + robot.CurrentPosition + " wanted "  + move + " got" + nextPossiblePosition);
    }

    private boolean transitionRobot(final Order moveCommand) throws RoomNotFound
    {
        Room transitionRoom;
        final Portal portal = currentRoom.getPortalFromPosition(getCurrentPosition());
        final boolean canTransition = portal != null;

        if(!canTransition)
        {
            return false; // No transition possible.
        }

        final World world = World.getInstance();
        transitionRoom = world.getRoom(portal.getToRoomID());

        // Swap rooms
        currentRoom.removeRobot(getUUID());
        transitionRoom.addRobot(this);

        //System.out.println("Transition from " +robot.CurrentPosition + " to " + portal.PointToPosition);

        setCurrentPosition(portal.getPointToPosition());

        return true;
    }

    private boolean setPositionRobot(final Order moveCommand) throws RoomNotFound
    {
        final World world = World.getInstance();
        final Room targetedRoom = world.getRoom(moveCommand.getGridID());
        final boolean isAtPosition = targetedRoom.isRobotAtPosition(new Vector2D(0,0));

        if(isAtPosition)
        {
            return false; // Robot is already at this position.
        }

        setCurrentPosition(new Vector2D(0,0));
        targetedRoom.addRobot(this);

        //System.out.println("Spawned at (0,0)");

        return true;
    }

    public boolean move(final Order moveCommand) throws RoomNotFound
    {
        boolean result = true;

        switch (moveCommand.getType())
        {
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
            {
                moveRobot(moveCommand);
                break;
            }
            case TRANSPORT:
            {
                result = transitionRobot(moveCommand);
                break;
            }
            case ENTER:
            {
                result = setPositionRobot(moveCommand);
                break;
            }
        }

        return result;
    }

    @Override
    public String toString()
    {
        return getCurrentPosition().toString() + " - "+ getName() + " - "+ getUUID();
    }

    public UUID getUUID()
    {
        return id;
    }

    private void setUUID(UUID uuid)
    {
        id = uuid;
    }

    public String getName()
    {
        return name;
    }

    private void setName(String string)
    {
        name = string;
    }

    public Vector2D getCurrentPosition()
    {
        return currentPosition;
    }

    private void setCurrentPosition(Vector2D position)
    {
        currentPosition = position;
    }

    public Room getRoom()
    {
        return currentRoom;
    }

    public void setRoom(Room room)
    {
        currentRoom = room;
    }
}
