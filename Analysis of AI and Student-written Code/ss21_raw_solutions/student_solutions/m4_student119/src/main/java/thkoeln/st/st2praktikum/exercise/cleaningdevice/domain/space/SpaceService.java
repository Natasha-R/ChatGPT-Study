package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpaceService implements ISpaceService {
    // private final List<SpaceConnectionEntity> connections = new ArrayList<>();
    // private final Map<UUID, IWalkable> devices = new ManagedMap<>();

    private final UUID uuid;
    private final SpaceRepository spaceRepository;
    private final CleaningDeviceRepository cleaningDeviceRepository;

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

    private void initBorders(Integer height, Integer width) {
        // Map borders
        barriers.add(new Barrier(new Point(0, 0), new Point(width, 0)));
        barriers.add(new Barrier(new Point(0, height), new Point(width, height)));
        barriers.add(new Barrier(new Point(0, 0), new Point(0, height)));
        barriers.add(new Barrier(new Point(width, 0), new Point(width, height)));
    }

    public SpaceService(Space entity, SpaceRepository spaceRepository, CleaningDeviceRepository cleaningDeviceRepository) {
        this.uuid = entity.getId();
        this.height = entity.getHeight();
        this.width = entity.getWidth();
        this.spaceRepository = spaceRepository;
        this.cleaningDeviceRepository = cleaningDeviceRepository;

        initBorders(height, width);

        for (Barrier barrierEntity : entity.getBarriers()) {
            final Point start = barrierEntity.getStart();
            final Point end = barrierEntity.getEnd();

            barriers.add(new Barrier(start, end));
        }
    }

    //public void addBarrier(Barrier barrier) {
    //    barriers.add(barrier);
    //}

    public List<Barrier> getBarriers() {
        return List.copyOf(barriers);
    }

    public void addDevice(UUID uuid) {
        final Optional<CleaningDevice> cleaningDeviceEntityOptional = cleaningDeviceRepository.findById(uuid);
        if(cleaningDeviceEntityOptional.isEmpty())
            throw new IllegalArgumentException();

        final Space spaceEntity = getSpaceEntity();
        spaceEntity.getCleaningDevices().add(cleaningDeviceEntityOptional.get());

        spaceRepository.save(spaceEntity);
        //devices.put(device.getUuid(), device);
    }

    public void removeDevice(IWalkable device) {
        final Space entity = getSpaceEntity();

        entity.getCleaningDevices().removeIf(cleaningDeviceEntity -> cleaningDeviceEntity.getId() == device.getUuid());
        spaceRepository.save(entity);
    }

    private Space getSpaceEntity() {
        final Optional<Space> spaceOptional = spaceRepository.getById(uuid);
        if (spaceOptional.isEmpty())
            throw new IllegalStateException();

        return spaceOptional.get();
    }


    public List<SpaceConnection> getSourceConnections() {
        final Space entity = getSpaceEntity();

        return entity.getSpaceConnections().stream()
                .filter(spaceConnection -> spaceConnection.getSource().getId() == this.uuid)
                .collect(Collectors.toList());
    }

    public boolean isFree(Point point) {
        final Space entity = getSpaceEntity();

        return entity.getCleaningDevices().stream().noneMatch(x -> {
            final Point p = x.getPoint();
            return p.getX().equals(point.getX()) && p.getY().equals(point.getY());
        });
    }
}

