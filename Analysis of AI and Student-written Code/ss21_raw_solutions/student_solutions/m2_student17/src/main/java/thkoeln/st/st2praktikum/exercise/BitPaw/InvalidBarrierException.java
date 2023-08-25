package thkoeln.st.st2praktikum.exercise.BitPaw;

public class InvalidBarrierException extends RuntimeException
{
    public InvalidBarrierException(String command)
    {
        super(command);
    }
}
