package thkoeln.st.st2praktikum.exercise.BitPaw;

import java.util.UUID;

public class Robot
{
    public final UUID ID;
    public final String Name;

    public Position CurrentPosition;

    public Robot(final String name)
    {
        ID = UUID.randomUUID();
        CurrentPosition = new Position(0,0);
        Name = name;
    }

    @Override
    public String toString()
    {
        return CurrentPosition.toString() + " - "+ Name + " - "+ ID;
    }
}
