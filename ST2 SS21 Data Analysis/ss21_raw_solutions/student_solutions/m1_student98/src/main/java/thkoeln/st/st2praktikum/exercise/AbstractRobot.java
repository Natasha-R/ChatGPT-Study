package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public abstract class AbstractRobot implements Identifiable {

    protected UUID id;
    protected String name;
    protected XYPositionable position;

    @Override
    public UUID getId() {
        return id;
    }

    public XYPositionable getPosition() {
        return position;
    }

    abstract public XYPositionable initializeIntoRoom(XYPositionable newPosition) throws PositionBlockedException;

    abstract public XYPositionable simpleMovement(XYMovable movement);

    abstract public XYPositionable transportRobot(Roomable targetRoom) throws NoDataFoundException, PositionBlockedException;

    abstract public void debugPrintRobotStatus();
}
