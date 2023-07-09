package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;

import java.util.UUID;

public class TidyUpRobot {
    @Getter
    UUID id;
    String name;
    @Getter
    Coordinate currentPosition;
    @Getter
    UUID roomId;

    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
