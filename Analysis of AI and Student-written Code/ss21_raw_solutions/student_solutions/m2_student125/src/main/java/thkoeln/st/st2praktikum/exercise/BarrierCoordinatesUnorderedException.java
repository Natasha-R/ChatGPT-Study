package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class BarrierCoordinatesUnorderedException extends RuntimeException
{
    public BarrierCoordinatesUnorderedException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
