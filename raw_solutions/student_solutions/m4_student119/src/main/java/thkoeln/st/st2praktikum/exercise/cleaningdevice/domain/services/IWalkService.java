package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;

public interface IWalkService {
    boolean walk(CommandType direction, int steps, IWalkable walkable, ISpaceService space);
}
