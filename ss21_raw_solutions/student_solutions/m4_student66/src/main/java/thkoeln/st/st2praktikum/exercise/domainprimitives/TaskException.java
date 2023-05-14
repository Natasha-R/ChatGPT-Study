package thkoeln.st.st2praktikum.exercise.domainprimitives;


import lombok.Getter;

@Getter
public class TaskException extends RuntimeException {


    public TaskException(String s) {
        super(s);
    }
}
