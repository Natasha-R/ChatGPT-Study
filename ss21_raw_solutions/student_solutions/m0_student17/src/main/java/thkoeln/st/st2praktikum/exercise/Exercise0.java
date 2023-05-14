package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.BitPaw.MoveCommand;
import thkoeln.st.st2praktikum.exercise.BitPaw.Point;
import thkoeln.st.st2praktikum.exercise.BitPaw.Room;

public class Exercise0 implements Moveable
{
    private final Room _room = new Room();

    @Override
    public String moveTo(String moveCommandString)
    {
        MoveCommand moveCommand = new MoveCommand(moveCommandString);
        Point currentPosition = _room.MoveRoboter(moveCommand);

        return currentPosition.toString();
    }
}
