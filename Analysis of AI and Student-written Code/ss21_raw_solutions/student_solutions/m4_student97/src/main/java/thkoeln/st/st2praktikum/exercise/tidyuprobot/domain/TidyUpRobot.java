package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
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
    private Point position;
    @Getter
    @ManyToOne (cascade = CascadeType.ALL)
    private Room room;
    @Getter
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Command> commands = new ArrayList<>();



    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Boolean isOccupied(Point position) {
        return (this.position.equals(position));
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public Boolean deleteCommandHistory() {
        this.commands = new ArrayList<>();
        return true;
    }

    public void placeRobot(Room destinationRoom, Point destinationPosition) {
        this.room = destinationRoom;
        this.position = destinationPosition;
    }

    public void moveRobot(Point destinationPosition) {
        this.position = destinationPosition;
    }

    public UUID getRoomId() {
        return room.getId();
    }

    public Point getPoint() {
        return position;
    }

    public void updateFromDto(TidyUpRobotDto tidyUpRobotDto) {
        if (!name.equals(tidyUpRobotDto.getName())) name = tidyUpRobotDto.getName();
    }
}
