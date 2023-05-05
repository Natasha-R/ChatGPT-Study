package thkoeln.st.st2praktikum.exercise.device;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.space.ISpaceService;

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

    ISpaceService getSpace();

    void setSpace(UUID uuid);

    UUID getUuid();

    Point currentPosition();
}
