package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class InvalidCoordinateFormatException extends RuntimeException
{
    public InvalidCoordinateFormatException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
