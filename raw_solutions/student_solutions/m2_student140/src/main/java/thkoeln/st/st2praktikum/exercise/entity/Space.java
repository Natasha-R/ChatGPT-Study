package thkoeln.st.st2praktikum.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<ObstacleEntity> obstacles = new ArrayList<>();

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


    public List<CleaningDevice> getCleaningDevices() { return this.cleaningDevices; }

    public UUID getSpaceId() { return this.spaceId; }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }
}
