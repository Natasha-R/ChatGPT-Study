package thkoeln.st.st2praktikum.exercise.exceptions;

public class InvalidCoordinateFormatException extends RuntimeException
{
    public InvalidCoordinateFormatException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
