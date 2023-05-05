package thkoeln.st.st2praktikum.exercise.World.Movable;

import thkoeln.st.st2praktikum.exercise.World.Exception.UUIDNotFoundException;
import thkoeln.st.st2praktikum.exercise.IItemsWithUUIDCollection;
import thkoeln.st.st2praktikum.exercise.World.Point;
import thkoeln.st.st2praktikum.exercise.World.Space.Space;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MovableCollection implements IItemsWithUUIDCollection<IMovable> {
    private final List<IMovable> cleaningDeviceList = new LinkedList<>();

    @Override
    public void add(IMovable someCleaningDevice) {
        this.cleaningDeviceList.add(someCleaningDevice);
    }

    @Override
    public IMovable findByUUID(UUID itemUUID) {
        Iterator<IMovable> cleaningDeviceIterator = this.cleaningDeviceList.iterator();
        IMovable currentSpace;

        while(cleaningDeviceIterator.hasNext())
        {
            currentSpace = cleaningDeviceIterator.next();

            if(!currentSpace.getUUID().equals(itemUUID))
            {
                continue;
            }

            return currentSpace;
        }

        throw new UUIDNotFoundException();
    }

    public boolean isFieldBlocked(Space space, Point field)
    {
        Iterator<IMovable> movableIterator = this.cleaningDeviceList.iterator();
        IMovable movable;

        while(movableIterator.hasNext())
        {
            movable = movableIterator.next();

            if(null == movable.getSpace() || !movable.getSpace().equals(space))
            {
                continue;
            }

            if(movable.getPosition().equals(field))
            {
                System.out.println(movable.getUUID());
                return true;
            }
        }

        return false;
    }
}
