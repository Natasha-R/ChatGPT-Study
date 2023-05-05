package thkoeln.st.st2praktikum.exercise.exceptions;

public class BarrierCoordinatesUnorderedException extends RuntimeException
{
    public BarrierCoordinatesUnorderedException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
