package thkoeln.st.st2praktikum.exercise.BitPaw;

public class InvalidPositionException extends Exception
{
    public InvalidPositionException(String name, Position position)
    {
        super("Invalid Positon at <" + name + "> with <" + position.toString() + ">");
    }
}