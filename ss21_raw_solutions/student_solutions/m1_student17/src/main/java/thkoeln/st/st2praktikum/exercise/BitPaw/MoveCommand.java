package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.UUID;

public class MoveCommand
{
    public CommandType Type;
    public UUID RobotUUID;

    //--[ Usec if "CommandType" is everything but move. ]----
    public UUID TargetRoomID;
    //-------------------------------------------------------

    //--[ Used if "CommandType" is Move. ]------------------
    public WallOrientation Orientation;
    public Direction MoveDirection;
    public int MoveSteps;
    //-------------------------------------------------------

    public MoveCommand(final UUID robotUUID, String string)
    {
        Type = CommandType.Invalid;
        MoveDirection = Direction.None;
        RobotUUID = robotUUID;
        MoveSteps = 0;
        TargetRoomID = null;

        final String directionWest = "we";
        final String directionNorth = "no";
        final String directionEast = "ea";
        final String directionSouth = "so";
        final String transition = "tr";
        final String initialPosition = "en";

        // Safe Check
        {
            boolean validLengh = string.length() >= 6;
            boolean hasBrckets;

            if(!validLengh)
            {
                throw new IllegalArgumentException("Command is far to small");
            }

            hasBrckets = string.charAt(0) == '[' && string.charAt(string.length()-1) == ']';

            if(!hasBrckets)
            {
                throw new IllegalArgumentException("Command has invalid Brackets -> []");
            }
        }

        final String tags = string.substring(1, 3);
        final int comma = string.indexOf(',');
        final String valueString = string.substring(comma+1, string.length()-1);

        switch (tags)
        {
            case directionWest:
                MoveDirection = Direction.Left;
                Orientation = WallOrientation.Horizontal;
                break;

            case directionNorth:
                MoveDirection = Direction.Up;
                Orientation = WallOrientation.Vertical;
                break;

            case directionEast:
                MoveDirection = Direction.Right;
                Orientation = WallOrientation.Horizontal;
                break;

            case directionSouth:
                MoveDirection = Direction.Down;
                Orientation = WallOrientation.Vertical;
                break;

            case transition:
                Type = CommandType.Transition;
                break;

            case initialPosition:
                Type = CommandType.SetPosition;
                break;

               default:
                throw new IllegalArgumentException("Invalid command");
        }

        if(MoveDirection != Direction.None)
        {
            Type = CommandType.Move;
        }

        switch (Type)
        {
            case Move:
                MoveSteps = Integer.parseInt(valueString);
                break;

            case Transition:
            case SetPosition:
                TargetRoomID = UUID.fromString(valueString);
                break;
        }
    }

    @Override
    public String toString()
    {
        return MoveDirection.name() + " " + MoveSteps;
    }
}