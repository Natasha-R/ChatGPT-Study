package thkoeln.st.st2praktikum.exercise.World.Obstacle;

import thkoeln.st.st2praktikum.exercise.ICollection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ObstacleCollection implements ICollection<Obstacle> {
    private final List<Obstacle> obstacles = new LinkedList<>();

    public void add(Obstacle someObstacle)
    {
        this.obstacles.add(someObstacle);
    }

    public Iterator<Obstacle> getIterator()
    {
        return this.obstacles.iterator();
    }
}
