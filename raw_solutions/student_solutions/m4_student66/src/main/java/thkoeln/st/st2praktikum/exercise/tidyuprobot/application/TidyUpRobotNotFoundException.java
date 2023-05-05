package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TidyUpRobotNotFoundException extends EntityNotFoundException {
    public TidyUpRobotNotFoundException(String message){super(message);}
}
