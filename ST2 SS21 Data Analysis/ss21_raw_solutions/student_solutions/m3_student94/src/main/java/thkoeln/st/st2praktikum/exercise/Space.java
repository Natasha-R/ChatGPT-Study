package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
public class Space {
    @Id
    private UUID spaceId;

    private Integer spaceHeight;
    private Integer spaceWidth;

    @Embedded
    private Coordinate initialCoordinate;

    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private List<Obstacle> obstacleList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<CleaningDevice> occupiedList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private  List<Connection> connectionsList;

    public Space(int height, int width){
        this.spaceId=UUID.randomUUID();
        this.spaceHeight =height;
        this.spaceWidth = width;
        this.initialCoordinate = new Coordinate(0,0);
    }

    public Space() { }

    public void addObstacle(Obstacle obstacle) {
        obstacleList.add(obstacle);
    }
    public void addOccupied(CleaningDevice cleaningDevice){
        occupiedList.add(cleaningDevice);
    }
    public void removeOccupied(CleaningDevice occupied) {
        occupiedList.remove(occupied);
    }

    public int getHeight() {
       return this.spaceHeight;
    }

    public int getWidth() {
        return this.spaceWidth;
    }



}
