package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import thkoeln.st.st2praktikum.exercise.domainprimitives.BaseException;

@ResponseStatus (code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid data for CleaningDevice creation")
public class CleaningDeviceCreationException extends BaseException {
    public CleaningDeviceCreationException(String message){super(message);}
}
