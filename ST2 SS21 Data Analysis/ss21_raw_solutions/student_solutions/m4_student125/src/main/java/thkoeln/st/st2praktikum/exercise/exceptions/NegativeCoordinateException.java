package thkoeln.st.st2praktikum.exercise.exceptions;

public class NegativeCoordinateException extends RuntimeException
{
    public NegativeCoordinateException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
