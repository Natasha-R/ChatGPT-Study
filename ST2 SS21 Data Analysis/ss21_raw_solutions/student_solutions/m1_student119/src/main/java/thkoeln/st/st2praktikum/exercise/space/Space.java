package thkoeln.st.st2praktikum.exercise.space;

import org.springframework.beans.factory.support.ManagedMap;
import thkoeln.st.st2praktikum.exercise.IWalkable;
import thkoeln.st.st2praktikum.exercise.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.barrier.BarrierType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Space implements ISpace {
    private final Map<UUID, SpaceConnection> connections = new ManagedMap<>();
    private final Map<UUID, IWalkable> devices = new ManagedMap<>();

    private final UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    private final Integer height;

    public Integer getHeight() {
        return height;
    }

    private final Integer width;

    public Integer getWidth() {
        return width;
    }

    private final List<Barrier> barriers = new ArrayList<>();

    public Space(UUID uuid, Integer height, Integer width) {
        this.uuid = uuid;
        this.height = height;
        this.width = width;

        // Map borders
        barriers.add(new Barrier(new Point(0, 0), new Point(width, 0), BarrierType.horizontal));
        barriers.add(new Barrier(new Point(0, height), new Point(width, height), BarrierType.horizontal));
        barriers.add(new Barrier(new Point(0, 0), new Point(0, height), BarrierType.vertical));
        barriers.add(new Barrier(new Point(width, 0), new Point(width, height), BarrierType.vertical));
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public List<Barrier> getBarriers() {
        return List.copyOf(barriers);
    }

    public void addDevice(IWalkable device) {
        devices.put(device.getUuid(), device);
    }

    public void removeDevice(IWalkable device) {
        devices.remove(device.getUuid());
    }

    public void addConnection(SpaceConnection connection) {
        connections.put(connection.getUuid(), connection);
    }

    public List<SpaceConnection> getSourceConnections() {
        return connections.values().stream()
                .filter(spaceConnection -> spaceConnection.getSource().getUuid() == this.uuid)
                .collect(Collectors.toList());
    }

    public boolean isFree(Point point) {
        return devices.values().stream().noneMatch(x -> x.currentPosition().equals(point));
    }
}

