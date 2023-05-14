package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ObstacleEntity {
    @Id
    private UUID obstacleId;
    private Integer sourceX;
    private Integer sourceY;
    private Integer destX;
    private Integer destY;

    @ManyToOne
    @JoinColumn(name = "Space_ID")
    private Space space;


    public ObstacleEntity(UUID obstacleId, Obstacle obstacle, Space space) {
        this.obstacleId = obstacleId;
        this.sourceX = obstacle.getStart().getX();
        this.sourceY = obstacle.getStart().getY();
        this.destX = obstacle.getEnd().getX();
        this.destY = obstacle.getEnd().getY();
        this.space = space;
    }

}
