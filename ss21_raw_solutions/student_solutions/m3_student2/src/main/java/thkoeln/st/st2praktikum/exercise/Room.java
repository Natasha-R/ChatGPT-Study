package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Room extends AbstractEntity implements RoomInterface{
    @Setter
    @Getter
    private Integer height;

    @Setter
    @Getter
    private Integer width;

    @Getter
    @ElementCollection(targetClass = Vector2D.class)
    @JoinColumn
    @JoinTable
    private List<Obstacle> obstacles;

    @OneToMany
    private List<Connection> connections;

    @Getter
    @ElementCollection(targetClass = Vector2D.class)
    @JoinTable
    @JoinColumn
    private List<Vector2D> cellOccupied;

    public Room(){
        obstacles = new ArrayList<>();
        connections = new ArrayList<>();
        cellOccupied = new ArrayList<>();
    }

    @Override
    public void addObstacles(Obstacle obstacle){
        obstacles.add(obstacle);
    }

    @Override
    public void addConnections(Connection connection) {
        connections.add(connection);
    }

    public void addCellOccupied(Vector2D vector2D){
        cellOccupied.add(vector2D);
    }

    @Override
    public void deleteAllObstacle(){
        obstacles.clear();
    }

    @Override
    public void deleteObstacle(Obstacle obstacle){
        obstacles.remove(obstacle);
    }

    @Override
    public Connection getConnection(UUID destinationRoomId) {
        for(int i=0; i<connections.size(); i++){
            if(connections.get(i).getDestinationRoomId().equals(destinationRoomId)){
                return connections.get(i);
            }
        }
        return null;
    }
}
