package thkoeln.st.st2praktikum.exercise.map;

import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;

import java.util.UUID;

public interface Queryable {
 SpaceManageable getSpaceByItsId (UUID spaceId);
 Instructable getCleaningDeviceByItsId(UUID cleaningDeviceId);
}
