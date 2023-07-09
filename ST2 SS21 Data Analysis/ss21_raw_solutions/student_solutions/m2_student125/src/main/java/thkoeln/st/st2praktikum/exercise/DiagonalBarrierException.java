package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class DiagonalBarrierException extends RuntimeException
{
    public DiagonalBarrierException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
