package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class DeviceFactory {

    public static HashMap<UUID, DeviceInterface> devices = new HashMap<>();
    public static DeviceInterface CreateDevice(String name){
        CleaningDevice device = new CleaningDevice(name);
        devices.put(device.getId(), device);
        return device;
    }

    public static HashMap<UUID, DeviceInterface> getDevices() {
        return devices;
    }

}
