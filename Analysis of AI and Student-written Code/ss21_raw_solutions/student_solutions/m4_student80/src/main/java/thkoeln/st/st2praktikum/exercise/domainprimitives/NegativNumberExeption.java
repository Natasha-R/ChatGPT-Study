package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class NegativNumberExeption  extends RuntimeException {
    public NegativNumberExeption(String errorMessage) {
        super(errorMessage);
    }
}