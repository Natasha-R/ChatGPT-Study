package thkoeln.st.st2praktikum.exercise.World.Space;

import thkoeln.st.st2praktikum.exercise.World.Exception.UUIDNotFoundException;
import thkoeln.st.st2praktikum.exercise.ICollection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SpaceCollection implements ICollection<Space> {
    private final List<Space> spaces = new LinkedList<>();

    public void add(Space someSpace) {
        this.spaces.add(someSpace);
    }

    public Space findByUUID(UUID spaceUUID) {
        Iterator<Space> spaceIterator = this.spaces.iterator();
        Space currentSpace;

        while(spaceIterator.hasNext())
        {
            currentSpace = spaceIterator.next();

            if(!currentSpace.getUUID().equals(spaceUUID))
            {
                continue;
            }

            return currentSpace;
        }

        throw new UUIDNotFoundException();
    }
}
