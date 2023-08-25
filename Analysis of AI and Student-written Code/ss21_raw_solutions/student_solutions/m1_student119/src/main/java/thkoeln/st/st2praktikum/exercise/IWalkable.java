package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.space.ISpace;

import java.awt.*;
import java.util.UUID;

public interface IWalkable {
    Point leftBottom();

    Point leftTop();

    Point rightTop();

    Point rightBottom();

    void walk(Direction direction, int steps);

    int getBorder(Direction direction);

    void jump(Point destinationPosition);

    ISpace getSpace();

    void setSpace(ISpace destination);

    UUID getUuid();

    Point currentPosition();
}
