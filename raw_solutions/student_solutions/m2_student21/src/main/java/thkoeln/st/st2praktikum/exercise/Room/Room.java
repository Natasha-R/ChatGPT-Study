package thkoeln.st.st2praktikum.exercise.Room;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Connection.Connection;
import thkoeln.st.st2praktikum.exercise.Connection.ConnectionPacket;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Robot.Blockable;

import java.util.ArrayList;
import java.util.UUID;

public class Room implements MoveAllowed {

    @Getter
    private UUID id;
    @Getter
    private Integer height;
    @Getter
    private Integer width;

    private ArrayList<Obstacle> obstacles;
    private ArrayList<Blockable> robots;
    private ArrayList<ConnectionPacket> connections;

    public Room(Integer height, Integer width){
        this.id = UUID.randomUUID();
        this.height = height;
        this.width = width;
        obstacles = new ArrayList<>();
        robots = new ArrayList<>();
        connections = new ArrayList<>();
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
        for(ConnectionPacket connection : connections){
            if(connection.validateConnection(this, coordinate, destinationRoom)){
                return true;
            }
        }
        return false;
    }

    public Coordinate getDestinationCoordinate(Coordinate coordinate, Room destinationRoom){
        for(ConnectionPacket connection : connections){
            if(connection.validateConnection(this, coordinate, destinationRoom)){
                return connection.getDestinationCoordinate();
            }
        }
        throw new RuntimeException("This coordinate has no Connection! Your coordinate: " + coordinate);
    }

    public void addRobot(Blockable robot){
        robots.add(robot);
    }

    public void removeRobot(Blockable robot){
        robots.remove(robot);
    }

    public void addConnectionPacket(Connection connection){
        connections.add(connection);
    }

    public void addObstacle(Obstacle obstacle){
        if(withinBoundary(obstacle.getStart()) && withinBoundary(obstacle.getStart())){
            obstacles.add(obstacle);
        }else{
            throw new RuntimeException("Obstacles cannot reach outside of the room!");
        }
    }

}