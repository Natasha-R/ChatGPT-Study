package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class NoDiagonalBarriersAllowed extends RuntimeException{
    public NoDiagonalBarriersAllowed(String errorMessage) {
        super(errorMessage);
    }
}