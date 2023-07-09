package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class NegativeCoordinateException extends RuntimeException
{
    public NegativeCoordinateException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
