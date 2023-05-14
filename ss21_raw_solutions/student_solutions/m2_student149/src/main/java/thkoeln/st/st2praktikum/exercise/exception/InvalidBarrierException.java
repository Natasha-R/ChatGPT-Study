package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.barrier.MyBarrier;

public class InvalidBarrierException extends RuntimeException {
    public InvalidBarrierException(String message, MyBarrier barrier) {
        super(message + ": " + barrier);
    }
}
