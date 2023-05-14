package thkoeln.st.st2praktikum.exercise.World.Space;

import thkoeln.st.st2praktikum.exercise.World.Movable.MovableCollection;
import thkoeln.st.st2praktikum.exercise.World.IHasUUID;
import thkoeln.st.st2praktikum.exercise.World.Movable.MovementDirectionEnum;
import thkoeln.st.st2praktikum.exercise.World.Obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.World.Obstacle.ObstacleCollection;
import thkoeln.st.st2praktikum.exercise.World.Point;

import java.util.Iterator;
import java.util.UUID;

public class Space implements IHasUUID {
    private final UUID uuid;
    private final ObstacleCollection obstacleCollection;
    private final MovableCollection movableCollection;
    private final SpaceConnectionCollection spaceConnectionCollection;

    public Space()
    {
        this.uuid = UUID.randomUUID();
        this.obstacleCollection = new ObstacleCollection();
        this.movableCollection = new MovableCollection();
        this.spaceConnectionCollection = new SpaceConnectionCollection();
    }

    public Space(int spaceHeight, int spaceWidth)
    {
        this();

        int spaceOuterHeight = spaceHeight + 1;
        int spaceOuterWidth = spaceWidth + 1;

        this.obstacleCollection.add(new Obstacle(new Point(0, 0), new Point(spaceOuterWidth, 0)));
        this.obstacleCollection.add(new Obstacle(new Point(0, 0), new Point(0, spaceOuterHeight)));
        this.obstacleCollection.add(new Obstacle(new Point(0, spaceOuterHeight), new Point(spaceOuterWidth, spaceOuterHeight)));
        this.obstacleCollection.add(new Obstacle(new Point(spaceOuterWidth, 0), new Point(spaceOuterWidth, spaceOuterHeight)));
    }

    public boolean isVectorColliding(Point current, Point next)
    {
        Iterator<Obstacle> obstacleIterator = this.obstacleCollection.getIterator();
        Obstacle someObstacle;

        while(obstacleIterator.hasNext())
        {
            someObstacle = obstacleIterator.next();

            if(someObstacle.isVectorColliding(current, next))
            {
                return true;
            }
        }

        return false;
    }

    public ObstacleCollection getObstacleCollection() {
        return obstacleCollection;
    }

    public MovableCollection getMovableCollection() {
        return movableCollection;
    }

    public SpaceConnectionCollection getSpaceConnectionCollection() {
        return this.spaceConnectionCollection;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    public UUID addConnectionToSpace(Point sourceField, Space destinationSpace, Point destinationField)
    {
        SpaceConnection spaceConnection = new SpaceConnection(this, sourceField, destinationSpace, destinationField);
        this.spaceConnectionCollection.add(spaceConnection);

        return spaceConnection.getUUID();
    }
}
