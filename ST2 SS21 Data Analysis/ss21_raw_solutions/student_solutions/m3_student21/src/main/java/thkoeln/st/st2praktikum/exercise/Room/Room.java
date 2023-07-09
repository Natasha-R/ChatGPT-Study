package thkoeln.st.st2praktikum.exercise.Room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Connection.Transportable;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Blockable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Room implements MoveAllowed {

    @Getter
    @Id
    private UUID id;
    @Getter
    private Integer height;
    @Getter
    private Integer width;

    @Transient
    private List<Obstacle> obstacles = new ArrayList<>();
    @Transient
    private List<Blockable> robots = new ArrayList<>();
    @Transient
    private List<Transportable> connections = new ArrayList<>();

    public Room(Integer height, Integer width){
        this.id = UUID.randomUUID();
        this.height = height;
        this.width = width;
    }

    public void addObstacle(Obstacle obstacle){
        if(withinBoundary(obstacle.getStart()) && withinBoundary(obstacle.getStart())){
            obstacles.add(obstacle);
        }else{
            throw new RuntimeException("Obstacles cannot reach outside of the room!");
        }
    }

    @Override
    public boolean obstacleCollision(Coordinate point1, Coordinate point2){
        for(Obstacle obstacle : obstacles){
            if(obstacle.pointCollision(point1) && obstacle.pointCollision(point2)){
                return true;
            }
        }
        return false;
    }

    public boolean validateConnectionPacket(Coordinate coordinate, Room destinationRoom){
        for(Transportable connection : connections){
            if(connection.validateConnection(coordinate, destinationRoom)){
                return true;
            }
        }
        return false;
    }

    public Coordinate getDestinationCoordinate(Coordinate coordinate, Room destinationRoom){
        for(Transportable connection : connections){
            if(connection.validateConnection(coordinate, destinationRoom)){
                return connection.getDestinationCoordinate();
            }
        }
        throw new RuntimeException("This coordinate has no Connection! Your coordinate: " + coordinate);
    }

    @Override
    public boolean canIGoThere(Coordinate coordinate) {
        return (withinBoundary(coordinate) && positionFree(coordinate));
    }

    public boolean positionFree(Coordinate coordinate){
        for(Blockable robot : robots){
            if(robot.positionBlocked(coordinate)){
                return false;
            }
        }
        return true;
    }

    public boolean withinBoundary(Coordinate coordinate){
        return (withinHeight(coordinate) && withinWidth(coordinate));
    }

    private boolean withinHeight(Coordinate coordinate){
        return (0 <= coordinate.getY() && coordinate.getY() < height);
    }

    private boolean withinWidth(Coordinate coordinate){
        return (0 <= coordinate.getX() && coordinate.getX() < width);
    }

    public void addRobot(Blockable robot){
        robots.add(robot);
    }

    public void removeRobot(Blockable robot){
        robots.remove(robot);
    }

    public void addTransportable(Transportable connection){
        connections.add(connection);
    }
}