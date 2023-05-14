package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;

import java.util.HashMap;
import java.util.UUID;

public class Map implements Operable {

    private final HashMap<UUID, SpaceManageable> spaceList = new HashMap<UUID, SpaceManageable>();
    private final HashMap<UUID, Instructable> cleaningDeviceList = new HashMap<UUID, Instructable>();

    @Override
    public void addSpace(SpaceManageable space) {
        this.spaceList.put(space.getId(),space);
    }

    @Override
    public void addCleaningDevice(Instructable cleaningDevice) {
        this.cleaningDeviceList.put(cleaningDevice.getId(),cleaningDevice);
    }

    @Override
    public SpaceManageable getSpaceByItsId(UUID spaceId) {
        return this.spaceList.get(spaceId);
    }

    @Override
    public Instructable getCleaningDeviceByItsId(UUID cleaningDeviceId) {
        return this.cleaningDeviceList.get(cleaningDeviceId);
    }
}
