package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class ConnectionOutOfBoundsException extends RuntimeException
{
    public ConnectionOutOfBoundsException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
