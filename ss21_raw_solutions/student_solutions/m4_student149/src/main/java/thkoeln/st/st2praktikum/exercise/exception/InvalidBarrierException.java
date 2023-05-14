package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.space.domain.barrier.MyBarrier;

public class InvalidBarrierException extends RuntimeException {
    public InvalidBarrierException(String message, MyBarrier barrier) {
        super(message + ": " + barrier);
    }
}
