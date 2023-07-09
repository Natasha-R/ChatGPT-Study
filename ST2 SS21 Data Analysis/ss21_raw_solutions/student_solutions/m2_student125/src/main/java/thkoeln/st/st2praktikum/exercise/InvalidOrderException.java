package thkoeln.st.st2praktikum.exercise;
import java.lang.RuntimeException;

public class InvalidOrderException extends RuntimeException
{
    public InvalidOrderException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
