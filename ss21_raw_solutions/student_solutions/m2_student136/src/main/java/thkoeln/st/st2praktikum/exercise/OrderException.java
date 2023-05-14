package thkoeln.st.st2praktikum.exercise;

public class OrderException extends RuntimeException
{
    public OrderException(){}

    public OrderException(String message)
    {
        super(message);
    }
}