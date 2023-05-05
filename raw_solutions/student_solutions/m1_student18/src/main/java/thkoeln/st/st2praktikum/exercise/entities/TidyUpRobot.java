package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
public class TidyUpRobot  {
    @Getter
    private final String name;
    @Getter
    @Setter
    private Coordinates robotPosition;
    @Getter
    @Setter
    private UUID roomID;

}
