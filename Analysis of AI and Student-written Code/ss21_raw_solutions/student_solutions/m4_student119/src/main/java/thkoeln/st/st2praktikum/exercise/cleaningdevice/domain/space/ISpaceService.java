package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.SpaceConnection;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;

import java.util.List;
import java.util.UUID;

public interface ISpaceService {
    // void addBarrier(Barrier barrier);

    void addDevice(UUID deviceId);

    void removeDevice(IWalkable device);

    // void addConnection(SpaceConnection connection);

    List<SpaceConnection> getSourceConnections();

    boolean isFree(Point point);

    List<Barrier> getBarriers();

    Integer getWidth();
    Integer getHeight();

    UUID getUuid();
}
