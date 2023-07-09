package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.exceptions.ObstacleOutOfBoundsException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
public class Room {
    @Id
    @Getter
    private final UUID ID = randomUUID();

    @Getter
    private final Integer height ,width;

    @ElementCollection
    private final List<Obstacle> Obstacles = new ArrayList<>();

    public Room(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }
    protected Room(){
        height = null;
        width = null;
    }

    public boolean checkOutOfBounds(Coordinate coordinate){
        assert this.height != null && this.width != null;
        return coordinate.getY() > this.height || coordinate.getX() > this.width;
    }

    public void addObstacle(String Coordinates){
        Obstacle obstacle = new Obstacle(Coordinates);
        if(this.checkOutOfBounds(obstacle.getStart()) || this.checkOutOfBounds(obstacle.getEnd()))
            throw new ObstacleOutOfBoundsException();

        Obstacles.add(obstacle);
    }

    public void addObstacle(Obstacle obstacle){
        if(this.checkOutOfBounds(obstacle.getStart()) || this.checkOutOfBounds(obstacle.getEnd()))
            throw new ObstacleOutOfBoundsException();

        Obstacles.add(obstacle);
    }

    //returns true when there's an obstacle in the direction
    public boolean checkForBlockingObstacle(Coordinate coordinate, DirectionsType direction){
        for (Obstacle obstacle : Obstacles)
            if (obstacle.blocksCoordinateInDirection(coordinate, direction))
                return true;
        return false;
    }

    public boolean inBounds(Coordinate coordinate){
        assert this.height != null && this.width != null;
        return coordinate.getY() < this.height || coordinate.getX() < this.width;
    }

    public boolean inBounds(int x, int y){
        if(x < 0 || y < 0)
            return false;
        else
            return inBounds(new Coordinate(x, y));
    }

}
