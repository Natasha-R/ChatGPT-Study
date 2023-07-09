package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Setter
@Getter
public class TidyUpRobotDto {
    private String name;
    private List<Order> orders;
    private Map<UUID, Room> rooms;
}
