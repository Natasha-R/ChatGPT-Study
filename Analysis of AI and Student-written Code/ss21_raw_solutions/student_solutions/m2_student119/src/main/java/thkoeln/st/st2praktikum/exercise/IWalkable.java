package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.ISpace;

import java.util.UUID;

public interface IWalkable {
    Point leftBottom();

    Point leftTop();

    Point rightTop();

    Point rightBottom();

    void walk(CommandType direction, int steps);
    Point previewWalk(CommandType direction, int steps);

    int getBorder(CommandType direction);

    void jump(Point destinationPosition);

    ISpace getSpace();

    void setSpace(ISpace destination);

    UUID getUuid();

    Point currentPosition();
}
