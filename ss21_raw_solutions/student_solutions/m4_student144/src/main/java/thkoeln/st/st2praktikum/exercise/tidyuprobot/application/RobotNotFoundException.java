package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends EntityNotFoundException {
    public RobotNotFoundException(String message){
        super(message);
    }
}
