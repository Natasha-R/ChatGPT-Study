package thkoeln.st.st2praktikum.exercise.domainprimitives;
public class InvalidStringError extends RuntimeException {
    public InvalidStringError(String errorMessage) {
        super(errorMessage);
    }
}