package thkoeln.st.st2praktikum.exercise.exception;

public class InvalidNumberOfStepsException extends RuntimeException
{
    public InvalidNumberOfStepsException(String command)
    {
        super(command);
    }
}