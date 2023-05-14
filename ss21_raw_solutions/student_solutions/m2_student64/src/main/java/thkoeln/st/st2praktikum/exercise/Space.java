package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Getter
public class Space implements IHasUUID {
    private final UUID uuid;

    private final List<Obstacle> listOfObstacles;
    private final List<Connection> listOfConnections;
    private final UUIDHashMap<IMovable> mapOfMovables;

    public Space()
    {
        this.uuid = UUID.randomUUID();

        this.listOfObstacles = new ArrayList<>();
        this.listOfConnections = new ArrayList<>();
        this.mapOfMovables = new UUIDHashMap<>();
    }

    public UUID getUUID() {
        return uuid;
    }

    public Space(int spaceHeight, int spaceWidth)
    {
        this();

        int spaceOuterHeight = spaceHeight + 1;
        int spaceOuterWidth = spaceWidth + 1;

        this.listOfObstacles.add(new Obstacle(new Point(0, 0), new Point(spaceOuterWidth, 0)));
        this.listOfObstacles.add(new Obstacle(new Point(0, 0), new Point(0, spaceOuterHeight)));
        this.listOfObstacles.add(new Obstacle(new Point(0, spaceOuterHeight), new Point(spaceOuterWidth, spaceOuterHeight)));
        this.listOfObstacles.add(new Obstacle(new Point(spaceOuterWidth, 0), new Point(spaceOuterWidth, spaceOuterHeight)));
    }

    public Connection getConnectionAtPoint(Point somePoint)
    {
        Iterator<Connection> connectionIterator = this.listOfConnections.iterator();
        System.out.println(String.format("-- Connection amount %s", this.listOfConnections.size()));

        Connection someConnection;
        while(connectionIterator.hasNext())
        {
            someConnection = connectionIterator.next();

            System.out.println(String.format("-- Connection %s", someConnection.getEndSpace().getUUID()));

            if(!someConnection.getStartPoint().equals(somePoint))
            {
                continue;
            }

            return someConnection;
        }

        return null;
    }

    public boolean useConnection(IMovable movable)
    {
        Connection connection = this.getConnectionAtPoint(movable.getPosition());
        if(null == connection)
        {
            return false;
        }

        System.out.println(String.format("Connection moving from %s to %s", connection.getStartSpace().getUUID(), connection.getEndSpace().getUUID()));

        movable.setSpace(connection.getEndSpace());
        movable.setPosition(connection.getEndpoint());

        return true;
    }

    public boolean isVectorColliding(Point current, Point next)
    {
        Iterator<Obstacle> obstacleIterator = this.listOfObstacles.iterator();
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

    public UUID addConnectionToSpace(Point sourceField, Space destinationSpace, Point destinationField)
    {
        if(this.isPointOutOfBounds(sourceField))
        {
            throw new RuntimeException();
        }

        if(destinationSpace.isPointOutOfBounds(destinationField))
        {
            throw new RuntimeException();
        }

        Connection spaceConnection = new Connection(this, sourceField, destinationSpace, destinationField);
        this.listOfConnections.add(spaceConnection);

        return spaceConnection.getUUID();
    }

    public void addObstacleToSpace(Obstacle obstacle)
    {
        if(this.isPointOutOfBounds(obstacle.getStart()))
        {
            throw new RuntimeException();
        }

        if(this.isPointOutOfBounds(obstacle.getEnd()))
        {
            throw new RuntimeException();
        }

        this.listOfObstacles.add(obstacle);
    }

    public int getHeight()
    {
        return this.listOfObstacles.get(1).getEnd().getY();
    }

    public int getWidth()
    {
        return this.listOfObstacles.get(0).getEnd().getX();
    }

    private boolean isPointOutOfBounds(Point point)
    {
        if(point.getX() > this.getHeight())
        {
            return true;
        }

        if(point.getY() > this.getWidth())
        {
            return true;
        }

        return false;
    }
}
