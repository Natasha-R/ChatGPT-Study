package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.space.ISpaceService;

public interface IWalkService {
    boolean walk(CommandType direction, int steps, IWalkable walkable, ISpaceService space);
}
