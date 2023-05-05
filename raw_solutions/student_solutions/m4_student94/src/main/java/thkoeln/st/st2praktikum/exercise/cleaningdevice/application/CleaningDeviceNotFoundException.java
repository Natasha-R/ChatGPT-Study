package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import javax.persistence.EntityNotFoundException;

public class CleaningDeviceNotFoundException extends EntityNotFoundException {
    public CleaningDeviceNotFoundException(String message){super(message);}
}
