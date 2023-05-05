package thkoeln.st.st2praktikum.exercise.repositories;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotRepository {
    @Getter
    private final HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
}
