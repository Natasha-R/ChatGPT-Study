package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.device.IWalkable;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.space.ISpaceService;

public class SetInitialSpaceService {
    public boolean setSpace(ISpaceService space, IWalkable walkable) {
        final boolean isFree = space.isFree(new Point(0, 0));
        if (walkable.getSpace() != null || !isFree)
            return false;

        space.addDevice(walkable.getUuid());
        walkable.setSpace(space.getUuid());
        walkable.jump(new Point(0, 0));
        return true;
    }
}
