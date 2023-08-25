package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

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