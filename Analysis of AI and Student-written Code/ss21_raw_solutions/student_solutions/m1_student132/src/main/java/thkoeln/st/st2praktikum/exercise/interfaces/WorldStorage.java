package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.entities.Connection;
import thkoeln.st.st2praktikum.exercise.entities.Space;

import java.util.HashMap;
import java.util.UUID;

public interface WorldStorage {
    HashMap<UUID, CleaningDevice> getCleaningDeviceMap();

    HashMap<UUID, Space> getSpaceMap();

    HashMap<UUID, Connection> getConnectionMap();
}
