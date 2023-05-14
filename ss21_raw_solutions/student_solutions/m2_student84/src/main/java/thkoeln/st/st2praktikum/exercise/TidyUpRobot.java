package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class TidyUpRobot {
    @Getter
    private UUID id;
    private String name;
    @Getter
    @Setter
    private Point currentPosition;
    @Getter
    @Setter
    private UUID roomId;

    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}