package thkoeln.archilab.ecommerce.usecases;

public class QuantityLimitExceededException extends RuntimeException {
    public QuantityLimitExceededException(String message) {
        super(message);
    }
}
