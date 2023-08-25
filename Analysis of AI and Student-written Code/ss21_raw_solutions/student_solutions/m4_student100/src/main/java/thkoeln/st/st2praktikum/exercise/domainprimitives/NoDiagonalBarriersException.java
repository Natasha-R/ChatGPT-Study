package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class NoDiagonalBarriersException extends RuntimeException{
    public NoDiagonalBarriersException(String errorMessage) {
        super(errorMessage);
    }
}
