package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.space.ISpace;

import java.awt.*;
import java.util.UUID;

public class CleaningDevice implements IWalkable {
    private final UUID uuid;
    private final String name;

    private final TilePosition position = new TilePosition(new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0));;

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

    String getCoordinates() {
        return position.getCoordinates();
    }

    public Point currentPosition() {
        return position.leftBottom;
    }

    @Override
    public Point leftBottom() {
        return position.leftBottom;
    }

    @Override
    public Point leftTop() {
        return position.leftTop;
    }

    @Override
    public Point rightTop() {
        return position.rightTop;
    }

    @Override
    public Point rightBottom() {
        return position.rightBottom;
    }

    @Override
    public void walk(Direction direction, int steps) {
        position.walk(direction, steps);
    }

    @Override
    public int getBorder(Direction direction) {
        return position.getBorder(direction);
    }

    @Override
    public void jump(Point destinationPosition) {
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

    public void walk(Direction direction, int steps) {
        var stepPoint = getStepPoint(direction, steps);
        updatePoint(leftBottom, stepPoint);
        updatePoint(leftTop, stepPoint);
        updatePoint(rightTop, stepPoint);
        updatePoint(rightBottom, stepPoint);
    }

    private void updatePoint(Point point, Point updates) {
        point.move(point.x + updates.x, point.y + updates.y);
    }

    private Point getStepPoint(Direction direction, int steps) {
        switch (direction) {
            case north:
                return new Point(0, steps);
            case east:
                return new Point(steps, 0);
            case south:
                return new Point(0, -steps);
            case west:
                return new Point(-steps, 0);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public int getBorder(Direction direction) {
        switch (direction) {
            case north:
                return leftTop.y;
            case east:
                return rightTop.x;
            case south:
                return rightBottom.y;
            case west:
                return leftTop.x;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public String getCoordinates() {
        return "(" + leftBottom.x + "," + leftBottom.y + ")";
    }

    public void jump(Point destinationPosition) {
        this.leftBottom.move(destinationPosition.x, destinationPosition.y);
        this.rightBottom.move(destinationPosition.x + 1, destinationPosition.y);
        this.leftTop.move(destinationPosition.x, destinationPosition.y + 1);
        this.rightTop.move(destinationPosition.x + 1, destinationPosition.y + 1);
    }
}