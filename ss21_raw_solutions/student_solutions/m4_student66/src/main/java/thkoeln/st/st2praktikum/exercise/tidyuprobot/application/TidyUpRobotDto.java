package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.application.RoomDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobotDto {
    private String name;
    private RoomDto room;
    private Point position;
}
