package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainapplication.DomainService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.room.domain.movement.ObstacleMovementValidation;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.Moveable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.MovementStatus;
import thkoeln.st.st2praktikum.exercise.domaintypes.Occupying;
import thkoeln.st.st2praktikum.exercise.domaintypes.Passable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Room {
    @Id
    @Getter
    private UUID id;
    @Getter
    private Integer roomWidth;
    @Getter
    private Integer roomHeight;

    @Getter
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Obstacle> obstacles = new ArrayList<>();


    public Room(Integer height, Integer width) {
        this.id = UUID.randomUUID();
        this.roomWidth = width;
        this.roomHeight = height;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
}
