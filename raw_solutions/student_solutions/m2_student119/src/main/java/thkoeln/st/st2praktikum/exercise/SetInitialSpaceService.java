package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.ISpace;

public class SetInitialSpaceService {
    boolean setSpace(ISpace space, IWalkable walkable) {
        final boolean isFree = space.isFree(new Point(0, 0));
        if (walkable.getSpace() != null || !isFree)
            return false;

        space.addDevice(walkable);
        walkable.setSpace(space);
        walkable.jump(new Point(0, 0));
        return true;
    }
}
