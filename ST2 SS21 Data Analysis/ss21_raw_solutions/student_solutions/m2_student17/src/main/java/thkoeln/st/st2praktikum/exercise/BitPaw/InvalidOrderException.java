package thkoeln.st.st2praktikum.exercise.BitPaw;

public class InvalidOrderException extends RuntimeException
{
    public InvalidOrderException(String command)
    {
        super(command);
    }
}
