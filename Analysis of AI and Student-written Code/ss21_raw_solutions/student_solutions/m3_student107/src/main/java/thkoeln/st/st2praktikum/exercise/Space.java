package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Space implements Unique {
    @Id
    private UUID id;

    private int width;

    private int height;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    @OneToMany
    private List<CleaningDevice> cleaningDeviceList = new ArrayList<CleaningDevice>();

    public void addObstacle(String obstacleString){
        this.obstacles.add(new Obstacle(obstacleString));
    }

    public void addObstacle(Obstacle obstacle){
        if(this.inBounds(obstacle.getStart()) && this.inBounds(obstacle.getEnd())) {
            this.obstacles.add(obstacle);
        } else throw new RuntimeException("Coordinate out of Bounds");
    }

    public Space(int height, int width){
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public List<CleaningDevice> getCleaningDeviceList() {
        return this.cleaningDeviceList;
    }






    public boolean inBounds(Coordinate coordinate){
        return coordinate.getX() < this.width
                && coordinate.getX() >= 0
                && coordinate.getY() < this.height
                && coordinate.getY() >= 0;
    }
}
