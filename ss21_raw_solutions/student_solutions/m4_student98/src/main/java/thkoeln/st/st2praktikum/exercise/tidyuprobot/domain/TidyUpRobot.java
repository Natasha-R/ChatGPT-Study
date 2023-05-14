package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.domaintypes.Occupying;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TidyUpRobotDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class TidyUpRobot implements Occupying {

    @Id
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private Vector2D position;
    @Getter
    @ManyToOne (cascade = CascadeType.ALL)
    private Room room;
    @Getter
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();



    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Boolean isOccupied(Vector2D position) {
        return (this.position.equals(position));
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Boolean deleteTaskHistory() {
        this.tasks = new ArrayList<>();
        return true;
    }

    public void placeRobot(Room destinationRoom, Vector2D destinationPosition) {
        this.room = destinationRoom;
        this.position = destinationPosition;
    }

    public void moveRobot(Vector2D destinationPosition) {
        this.position = destinationPosition;
    }

    public UUID getRoomId() {
        return room.getId();
    }

    public Vector2D getVector2D() {
        return position;
    }

    public void updateFromDto(TidyUpRobotDto tidyUpRobotDto) {
        if (!name.equals(tidyUpRobotDto.getName())) name = tidyUpRobotDto.getName();
    }
}
