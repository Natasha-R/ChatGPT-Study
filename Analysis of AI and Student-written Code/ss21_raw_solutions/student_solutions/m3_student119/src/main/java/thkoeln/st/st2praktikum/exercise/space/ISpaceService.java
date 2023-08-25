package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.entities.SpaceConnection;

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
