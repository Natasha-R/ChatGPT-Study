package thkoeln.st.st2praktikum.exercise;

import com.sun.istack.Nullable;
import lombok.*;
import thkoeln.st.st2praktikum.exercise.entities.Portal;
import thkoeln.st.st2praktikum.exercise.entities.Room;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.utility.World;

import javax.persistence.*;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class TidyUpRobot
{
    @Id
    private UUID uuid;

    @Embedded
    private String name;

    @Embedded
    private Vector2D currentPosition;

    private UUID currentRoomUUID;

    public TidyUpRobot(final String name)
    {
        setUUID(UUID.randomUUID());
        setCurrentPosition(new Vector2D(0,0));
        setName(name);
    }


    private  void moveRobot(final Order moveCommand)
    {
        final Vector2D nextPossiblePosition;

        try
        {
            nextPossiblePosition = World.getInstance().getRoom(currentRoomUUID).getNextPossiblePosition(this, moveCommand);

            setCurrentPosition(nextPossiblePosition);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }
    }

    private boolean transitionRobot(final Order moveCommand) throws RoomNotFound
    {
        Room transitionRoom;
        final Room currentRoom =  World.getInstance().getRoom(currentRoomUUID);
        final Portal portal = World.getInstance().getPortalAtPosition(currentRoom.getUUID(), currentPosition);
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
        return uuid;
    }

    private void setUUID(UUID uuid)
    {
        this.uuid = uuid;
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

    public void setRoomID(UUID room)
    {
        currentRoomUUID = room;
    }

    public UUID getRoomId()
    {
        return currentRoomUUID;
    }

    public Vector2D getVector2D()
    {
        return getCurrentPosition();
    }
}
