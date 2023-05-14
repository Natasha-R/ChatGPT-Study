package thkoeln.st.st2praktikum.exercise.exception;

public class InvalidBarrierException extends RuntimeException
{
    public InvalidBarrierException(String command)
    {
        super(command);
    }
}
