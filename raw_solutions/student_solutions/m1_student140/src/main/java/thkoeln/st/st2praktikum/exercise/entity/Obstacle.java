package thkoeln.st.st2praktikum.exercise.entity;

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
public class Obstacle {
    @Id
    private UUID obstacleId;
    private Integer sourceX;
    private Integer sourceY;
    private Integer destX;
    private Integer destY;

    @ManyToOne
    @JoinColumn(name = "Space_ID")
    private Space space;

    public Integer getSourceX() { return this.sourceX; }

    public Integer getSourceY() { return this.sourceY; }

    public Integer getDestX() { return this.destX; }

    public Integer getDestY() { return this.destY; }

    public Obstacle(UUID obstacleId, Integer sourceX, Integer sourceY, Integer destX, Integer destY, Space space) {
        this.obstacleId = obstacleId;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.destX = destX;
        this.destY = destY;
        this.space = space;
    }

    public UUID getObstacleId() { return this.obstacleId; }
}
