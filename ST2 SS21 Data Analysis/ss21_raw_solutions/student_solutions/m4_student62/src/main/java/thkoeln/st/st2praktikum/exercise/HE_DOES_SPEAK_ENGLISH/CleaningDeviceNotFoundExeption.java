package thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH;

import java.util.UUID;

public class CleaningDeviceNotFoundExeption extends RuntimeException{
    public CleaningDeviceNotFoundExeption(UUID cleaningdevice){
        super("CLeaningDevice: "+cleaningdevice+" not found!");
    }
}
