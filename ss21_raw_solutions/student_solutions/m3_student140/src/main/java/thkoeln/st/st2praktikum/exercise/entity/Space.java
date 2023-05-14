package thkoeln.st.st2praktikum.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Space {

    @Id
    private UUID spaceId;
    private Integer height;
    private Integer width;

    @OneToMany(
            mappedBy = "space",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ObstacleEntity> obstacles = new HashSet<ObstacleEntity>();
    //private List<ObstacleEntity> obstacles = new ArrayList<>();

    @OneToMany(
            mappedBy = "space",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CleaningDevice> cleaningDevices = new ArrayList<>();



    public Space(UUID spaceId, Integer height, Integer width) {
        this.spaceId = spaceId;
        this.height = height;
        this.width = width;
    }


    public List<Obstacle> getObstacles() {
        ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
        for (ObstacleEntity oldObstacle: this.obstacles) {
            Coordinate startCoordinate = new Coordinate(oldObstacle.getSourceX(),oldObstacle.getSourceY());
            Coordinate endCoordinate = new Coordinate(oldObstacle.getDestX(),oldObstacle.getDestY());
            Obstacle newObstacle = new Obstacle(startCoordinate,endCoordinate);
            obstacleList.add(newObstacle);
        }

        return obstacleList;
    }

    public Set<ObstacleEntity> getObstacleEntities() {
        return Collections.unmodifiableSet(this.obstacles);
    }

    public void addObstacle(ObstacleEntity obstacleEntity) {
        this.obstacles.add(obstacleEntity);
    }

    public void addCleaningDevice(CleaningDevice cleaningDevice) {
        this.cleaningDevices.add(cleaningDevice);
    }


    public List<CleaningDevice> getCleaningDevices() { return this.cleaningDevices; }

    public UUID getSpaceId() { return this.spaceId; }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }
}
