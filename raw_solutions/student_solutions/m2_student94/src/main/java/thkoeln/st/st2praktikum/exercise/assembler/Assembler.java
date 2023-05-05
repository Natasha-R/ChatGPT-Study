package thkoeln.st.st2praktikum.exercise.assembler;

import thkoeln.st.st2praktikum.exercise.assembler.Operable;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;

import java.util.HashMap;
import java.util.UUID;

public class Assembler implements Operable {

    private static final HashMap<UUID, SpaceManageable> spaceList = new HashMap<UUID, SpaceManageable>();
    private static final HashMap<UUID, Instructable> cleaningDeviceList = new HashMap<UUID, Instructable>();

    @Override
    public void addSpace(SpaceManageable space) {
        spaceList.put(space.getId(),space);
    }

    @Override
    public void addCleaningDevice(Instructable cleaningDevice) {
        cleaningDeviceList.put(cleaningDevice.getId(),cleaningDevice);
    }

    @Override
    public SpaceManageable getSpaceByItsId(UUID spaceId) {
        return spaceList.get(spaceId);
    }

    @Override
    public Instructable getCleaningDeviceByItsId(UUID cleaningDeviceId) {
        return cleaningDeviceList.get(cleaningDeviceId);
    }

    public static HashMap<UUID, SpaceManageable> getSpaceList() {
        return spaceList;
    }

    public static HashMap<UUID, Instructable> getCleaningDeviceList() {
        return cleaningDeviceList;
    }
}
