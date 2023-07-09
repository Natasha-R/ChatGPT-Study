package thkoeln.st.st2praktikum.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * javaDoc
 *
 * @author gloewen
 */
@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Entity not found")
public class NoSuchEntityException extends NoSuchElementException {
    public NoSuchEntityException(NoSuchElementException ex) {
        super(ex.getMessage() + ": " + ex.getCause().getMessage());
    }
}
