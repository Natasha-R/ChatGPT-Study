package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class CleaningDeviceNotFoundExeption extends Exception{
    public CleaningDeviceNotFoundExeption(UUID cleaningdevice){
        super("CLeaningDevice: "+cleaningdevice+" not found!");
    }
}
