package thkoeln.st.st2praktikum.exercise.domainprimitives;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends RuntimeException {
    public RobotNotFoundException( String message ) { super( message ); }
}
