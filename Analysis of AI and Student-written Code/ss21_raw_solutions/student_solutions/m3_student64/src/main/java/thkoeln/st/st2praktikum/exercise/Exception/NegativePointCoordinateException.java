package thkoeln.st.st2praktikum.exercise.Exception;

public class NegativePointCoordinateException extends RuntimeException {
    public NegativePointCoordinateException() {
        super("Die Koordinaten eines Punktes dürfen nicht negativ sein");
    }
}
