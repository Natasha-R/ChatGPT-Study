package thkoeln.st.st2praktikum.exercise.BitPaw;

public class InvalidNumberOfStepsException extends RuntimeException
{
    public InvalidNumberOfStepsException(String command)
    {
        super(command);
    }
}