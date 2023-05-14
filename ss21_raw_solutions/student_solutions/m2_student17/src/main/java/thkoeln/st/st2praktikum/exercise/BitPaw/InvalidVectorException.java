package thkoeln.st.st2praktikum.exercise.BitPaw;

public class InvalidVectorException extends RuntimeException
{
    public InvalidVectorException(String command)
    {
        super(command);
    }
}
