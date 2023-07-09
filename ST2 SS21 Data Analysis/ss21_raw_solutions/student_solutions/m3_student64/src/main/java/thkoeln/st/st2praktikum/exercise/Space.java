package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Space extends AbstractEntity {
    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private List<Obstacle> obstacles;

    @OneToMany(mappedBy="startSpace", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Connection> connections;

    @OneToMany(mappedBy="space", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CleaningDevice> cleaningDevices;

    public Space()
    {
        super();

        this.obstacles = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.cleaningDevices = new ArrayList<>();
    }

    public Space(int spaceHeight, int spaceWidth)
    {
        this();

        int spaceOuterHeight = spaceHeight + 1;
        int spaceOuterWidth = spaceWidth + 1;

        this.obstacles.add(new Obstacle(new Point(0, 0), new Point(spaceOuterWidth, 0)));
        this.obstacles.add(new Obstacle(new Point(0, 0), new Point(0, spaceOuterHeight)));
        this.obstacles.add(new Obstacle(new Point(0, spaceOuterHeight), new Point(spaceOuterWidth, spaceOuterHeight)));
        this.obstacles.add(new Obstacle(new Point(spaceOuterWidth, 0), new Point(spaceOuterWidth, spaceOuterHeight)));
    }

    public Connection getConnectionAtPoint(Point somePoint)
    {
        Iterator<Connection> connectionIterator = this.connections.iterator();
        System.out.printf("-- Connection amount %s%n", this.connections.size());

        Connection someConnection;
        while(connectionIterator.hasNext())
        {
            someConnection = connectionIterator.next();

            System.out.printf("-- Connection %s%n", someConnection.getEndSpace().getId());

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

        System.out.printf("Connection moving from %s to %s%n", connection.getStartSpace().getId(), connection.getEndSpace().getId());

        movable.setSpace(connection.getEndSpace());
        movable.setPosition(connection.getEndPoint());

        return true;
    }

    public boolean isVectorColliding(Point current, Point next)
    {
        Iterator<Obstacle> obstacleIterator = this.obstacles.iterator();
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

    public UUID addConnectionToSpace(TransportCategory transportCategory, Point sourceField, Space destinationSpace, Point destinationField)
    {
        if(this.isPointOutOfBounds(sourceField))
        {
            throw new RuntimeException();
        }

        if(destinationSpace.isPointOutOfBounds(destinationField))
        {
            throw new RuntimeException();
        }

        Connection spaceConnection = new Connection(transportCategory, this, sourceField, destinationSpace, destinationField);
        this.connections.add(spaceConnection);

        return spaceConnection.getId();
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

        this.obstacles.add(obstacle);
    }

    public int getHeight()
    {
        return this.obstacles.get(1).getEnd().getY();
    }

    public int getWidth()
    {
        return this.obstacles.get(0).getEnd().getX();
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
