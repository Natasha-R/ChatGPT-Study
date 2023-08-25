package thkoeln.st.st2praktikum.exercise.exception;

public class NegativeMovementStepsException extends IllegalArgumentException {
    public NegativeMovementStepsException(int steps) {
        super("Amount of movement steps has to be positive: " + steps);
    }
}
