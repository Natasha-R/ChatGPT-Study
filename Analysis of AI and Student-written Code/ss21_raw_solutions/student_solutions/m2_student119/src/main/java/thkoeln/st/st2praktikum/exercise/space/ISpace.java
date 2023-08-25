package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.IWalkable;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.List;
import java.util.UUID;

public interface ISpace {
    void addBarrier(Barrier barrier);

    void addDevice(IWalkable device);

    void removeDevice(IWalkable device);

    void addConnection(thkoeln.st.st2praktikum.exercise.space.SpaceConnection connection);

    List<thkoeln.st.st2praktikum.exercise.space.SpaceConnection> getSourceConnections();

    boolean isFree(Point point);

    List<Barrier> getBarriers();

    Integer getWidth();
    Integer getHeight();

    UUID getUuid();
}
