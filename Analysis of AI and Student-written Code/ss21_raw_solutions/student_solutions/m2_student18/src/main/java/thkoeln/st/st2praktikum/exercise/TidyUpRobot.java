package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
public class TidyUpRobot  {
    @Getter
    private String name;
    @Getter
    @Setter
    private Vector2D robotPosition;
    @Getter
    @Setter
    private UUID roomID;

}
