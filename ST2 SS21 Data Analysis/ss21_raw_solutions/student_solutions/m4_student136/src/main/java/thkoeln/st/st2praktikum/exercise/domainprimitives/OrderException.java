package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class OrderException extends RuntimeException
{
    public OrderException(){}

    public OrderException(String message)
    {
        super(message);
    }
}