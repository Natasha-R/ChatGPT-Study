package thkoeln.st.st2praktikum.exceptions;


import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {


    public TaskException(String s) {
        super(s);
    }
}
