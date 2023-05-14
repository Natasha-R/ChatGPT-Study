package thkoeln.st.st2praktikum.exercise.exceptions;

public class BarrierOutOfBoundsException extends RuntimeException
{
    public BarrierOutOfBoundsException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
