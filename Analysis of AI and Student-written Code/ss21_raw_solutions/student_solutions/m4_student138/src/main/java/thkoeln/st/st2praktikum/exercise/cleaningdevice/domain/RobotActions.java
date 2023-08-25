package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;

import java.util.HashMap;
import java.util.UUID;

public interface RobotActions {
    public boolean move(Space currentSpace, CommandType direction, int fields);
    public boolean transport(Space currentSpace, Space destinationSpace);
    public boolean enterSpace(Space destinationSpace);
}
