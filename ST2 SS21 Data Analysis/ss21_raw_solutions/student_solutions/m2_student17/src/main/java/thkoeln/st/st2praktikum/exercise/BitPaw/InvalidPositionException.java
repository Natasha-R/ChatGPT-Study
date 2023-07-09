package thkoeln.st.st2praktikum.exercise.BitPaw;

import thkoeln.st.st2praktikum.exercise.Vector2D;

public class InvalidPositionException extends RuntimeException
{
    public InvalidPositionException(String name)
    {
        super(name);
    }

    public InvalidPositionException(String name, Vector2D position)
    {
        super("Invalid Positon at <" + name + "> with <" + position.toString() + ">");
    }
}