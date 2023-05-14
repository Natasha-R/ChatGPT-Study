package thkoeln.st.st2praktikum.exercise;

public class CommandException extends RuntimeException{
    public CommandException(){}

    public CommandException(String message){
        super(message);
    }
}
