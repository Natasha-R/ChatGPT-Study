package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.IWalkable;
import thkoeln.st.st2praktikum.exercise.barrier.Barrier;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface ISpace {
    void addBarrier(Barrier barrier);

    void addDevice(IWalkable device);

    void removeDevice(IWalkable device);

    void addConnection(SpaceConnection connection);

    List<SpaceConnection> getSourceConnections();

    boolean isFree(Point point);

    List<Barrier> getBarriers();

    UUID getUuid();
}
