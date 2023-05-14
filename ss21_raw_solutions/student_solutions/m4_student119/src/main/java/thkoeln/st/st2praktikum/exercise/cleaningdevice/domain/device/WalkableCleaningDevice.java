package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.Space;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.SpaceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

public class WalkableCleaningDevice implements IWalkable {
    private final CleaningDevice entity;
    private final CleaningDeviceRepository repository;
    private final SpaceRepository spaceRepository;

    private final TilePosition position = new TilePosition(new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0));

    public WalkableCleaningDevice(CleaningDevice device, CleaningDeviceRepository repository, SpaceRepository spaceRepository) {
        this.entity = device;
        this.repository = repository;
        this.spaceRepository = spaceRepository;

        final thkoeln.st.st2praktikum.exercise.domainprimitives.Point p = device.getPoint();

        if (p != null) {
            position.jump(p);
        }
    }

    public UUID getUuid() {
        return entity.getId();
    }

    public ISpaceService getSpace() {
        if(entity.getSpace() == null)
            return null;

        return new SpaceService(entity.getSpace(), spaceRepository, repository);
    }

    @Override
    public void setSpace(UUID uuid) {
        final Optional<Space> spaceEntity =  spaceRepository.getById(uuid);
        if(spaceEntity.isEmpty())
            throw new IllegalArgumentException();

        entity.setSpace(spaceEntity.get());
        repository.save(entity);
    }

    public String getName() {
        return entity.getName();
    }

    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point currentPosition() {
        return getPoint(position.leftBottom);
    }

    private thkoeln.st.st2praktikum.exercise.domainprimitives.Point getPoint(Point point) {
        return convertPoint(point);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point leftBottom() {
        return getPoint(position.leftBottom);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point leftTop() {
        return getPoint(position.leftTop);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point rightTop() {
        return getPoint(position.rightTop);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point rightBottom() {
        return getPoint(position.rightBottom);
    }

    @Override
    public void walk(CommandType direction, int steps) {
        position.walk(direction, steps);
        saveCurrentPosition();
    }

    private void saveCurrentPosition() {
        entity.setPoint(leftBottom());
        repository.save(entity);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point previewWalk(CommandType direction, int steps) {
        final Point previewPoint = position.getPreviewPosition(direction, steps);
        return convertPoint(previewPoint);
    }

    private thkoeln.st.st2praktikum.exercise.domainprimitives.Point convertPoint(Point previewPoint) {
        return new thkoeln.st.st2praktikum.exercise.domainprimitives.Point(previewPoint.x, previewPoint.y);
    }

    @Override
    public int getBorder(CommandType direction) {
        return position.getBorder(direction);
    }

    @Override
    public void jump(thkoeln.st.st2praktikum.exercise.domainprimitives.Point destinationPosition) {
        position.jump(destinationPosition);
        saveCurrentPosition();
    }
}

class TilePosition {
    final public Point leftBottom;
    final public Point leftTop;
    final public Point rightTop;
    final public Point rightBottom;

    TilePosition(Point leftBottom, Point leftTop, Point rightTop, Point rightBottom) {
        this.leftBottom = leftBottom;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.rightBottom = rightBottom;
    }

    public void walk(CommandType direction, int steps) {
        var stepPoint = getStepPoint(direction, steps);
        updatePoint(leftBottom, stepPoint);
        updatePoint(leftTop, stepPoint);
        updatePoint(rightTop, stepPoint);
        updatePoint(rightBottom, stepPoint);
    }

    public Point getPreviewPosition(CommandType direction, int steps) {
        var stepPoint = getStepPoint(direction, steps);
        final Point point = new Point(leftBottom.x, leftBottom.y);
        updatePoint(point, stepPoint);
        return point;
    }

    private void updatePoint(Point point, Point updates) {
        point.move(point.x + updates.x, point.y + updates.y);
    }

    private Point getStepPoint(CommandType direction, int steps) {
        switch (direction) {
            case NORTH:
                return new Point(0, steps);
            case EAST:
                return new Point(steps, 0);
            case SOUTH:
                return new Point(0, -steps);
            case WEST:
                return new Point(-steps, 0);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public int getBorder(CommandType direction) {
        switch (direction) {
            case NORTH:
                return leftTop.y;
            case EAST:
                return rightTop.x;
            case SOUTH:
                return rightBottom.y;
            case WEST:
                return leftTop.x;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public thkoeln.st.st2praktikum.exercise.domainprimitives.Point getCoordinates() {
        return new thkoeln.st.st2praktikum.exercise.domainprimitives.Point(leftBottom.x, leftBottom.y);
    }

    public void jump(thkoeln.st.st2praktikum.exercise.domainprimitives.Point destinationPosition) {
        this.leftBottom.move(destinationPosition.getX(), destinationPosition.getY());
        this.rightBottom.move(destinationPosition.getX() + 1, destinationPosition.getY());
        this.leftTop.move(destinationPosition.getX(), destinationPosition.getY() + 1);
        this.rightTop.move(destinationPosition.getX() + 1, destinationPosition.getY() + 1);
    }
}