package thkoeln.st.st2praktikum.exercise.exceptions;

public class DiagonalBarrierException extends RuntimeException
{
    public DiagonalBarrierException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
