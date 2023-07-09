package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.Core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Setter
@Getter
public class Space extends AbstractEntity {
    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private List<Obstacle> obstacles;

    @OneToMany(mappedBy="space", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CleaningDevice> cleaningDevices;

    public Space()
    {
        super();

        this.obstacles = new ArrayList<>();
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

    public void addObstacle(Obstacle obstacle)
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

        return point.getY() > this.getWidth();
    }
}
