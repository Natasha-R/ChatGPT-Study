package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class BarrierOutOfBoundsException extends RuntimeException
{
    public BarrierOutOfBoundsException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
