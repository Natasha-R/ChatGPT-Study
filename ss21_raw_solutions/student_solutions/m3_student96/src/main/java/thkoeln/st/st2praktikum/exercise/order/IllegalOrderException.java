package thkoeln.st.st2praktikum.exercise.order;

public class IllegalOrderException extends RuntimeException{

    public IllegalOrderException( String message ) {
        super( message );
    }
}