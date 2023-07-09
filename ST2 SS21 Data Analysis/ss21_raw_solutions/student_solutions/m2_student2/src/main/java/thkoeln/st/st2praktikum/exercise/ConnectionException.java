package thkoeln.st.st2praktikum.exercise;

public class ConnectionException extends RuntimeException{
    public ConnectionException(){};

    public ConnectionException(String message){
        super(message);
    }
}
