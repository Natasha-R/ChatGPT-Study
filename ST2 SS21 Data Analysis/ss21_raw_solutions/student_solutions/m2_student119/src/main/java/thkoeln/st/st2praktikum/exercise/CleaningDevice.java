package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.ISpace;

import java.awt.Point;
import java.util.UUID;

public class CleaningDevice implements IWalkable {
    private final UUID uuid;
    private final String name;

    private final TilePosition position = new TilePosition(new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0));

    CleaningDevice(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ISpace getSpace() {
        return mSpace;
    }

    public void setSpace(ISpace space) {
        this.mSpace = space;
    }

    private ISpace mSpace;

    public String getName() {
        return name;
    }

    thkoeln.st.st2praktikum.exercise.Point getCoordinates() {
        return position.getCoordinates();
    }

    public thkoeln.st.st2praktikum.exercise.Point currentPosition() {
        return getPoint(position.leftBottom);
    }

    private thkoeln.st.st2praktikum.exercise.Point getPoint(Point point) {
        return new thkoeln.st.st2praktikum.exercise.Point(point.x, point.y);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.Point leftBottom() {
        return getPoint(position.leftBottom);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.Point leftTop() {
        return  getPoint(position.leftTop);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.Point rightTop() {
        return getPoint(position.rightTop);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.Point rightBottom() {
        return getPoint(position.rightBottom);
    }

    @Override
    public void walk(CommandType direction, int steps) {
        position.walk(direction, steps);
    }

    @Override
    public thkoeln.st.st2praktikum.exercise.Point previewWalk(CommandType direction, int steps) {
        final Point previewPoint = position.getPreviewPosition(direction, steps);
        return new thkoeln.st.st2praktikum.exercise.Point(previewPoint.x, previewPoint.y);
    }

    @Override
    public int getBorder(CommandType direction) {
        return position.getBorder(direction);
    }

    @Override
    public void jump(thkoeln.st.st2praktikum.exercise.Point destinationPosition) {
        position.jump(destinationPosition);
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

    public thkoeln.st.st2praktikum.exercise.Point getCoordinates() {
        return new thkoeln.st.st2praktikum.exercise.Point(leftBottom.x, leftBottom.y);
    }

    public void jump(thkoeln.st.st2praktikum.exercise.Point destinationPosition) {
        this.leftBottom.move(destinationPosition.getX(), destinationPosition.getY());
        this.rightBottom.move(destinationPosition.getX() + 1, destinationPosition.getY());
        this.leftTop.move(destinationPosition.getX(), destinationPosition.getY() + 1);
        this.rightTop.move(destinationPosition.getX() + 1, destinationPosition.getY() + 1);
    }
}