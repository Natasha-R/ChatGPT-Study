package thkoeln.st.st2praktikum.exercise.World.Space;

import thkoeln.st.st2praktikum.exercise.ICollection;
import thkoeln.st.st2praktikum.exercise.World.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpaceConnectionCollection implements ICollection<SpaceConnection> {
    private final List<SpaceConnection> spaceConnectionList = new ArrayList<>();

    @Override
    public void add(SpaceConnection spaceConnection)
    {
        this.spaceConnectionList.add(spaceConnection);
    }

    public SpaceConnection byPoint(Point somePoint)
    {
        Iterator<SpaceConnection> spaceIterator = this.spaceConnectionList.iterator();
        SpaceConnection currentSpaceConnection;

        while(spaceIterator.hasNext())
        {
            currentSpaceConnection = spaceIterator.next();

            if(!currentSpaceConnection.getSourcePosition().equals(somePoint))
            {
                continue;
            }

            return currentSpaceConnection;
        }

        return null;
    }
}
