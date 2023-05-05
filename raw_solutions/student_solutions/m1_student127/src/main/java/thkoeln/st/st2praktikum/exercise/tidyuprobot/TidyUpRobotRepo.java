package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.Walkable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TidyUpRobotRepo {
    private final HashMap<UUID, TidyUpRobot> tidyUpRobots;

    public void add(TidyUpRobot newTidyUpRobot) {
        tidyUpRobots.put(newTidyUpRobot.getId(), newTidyUpRobot);
    }
    public void removeById(UUID tidyUpRobotId) {
        tidyUpRobots.remove(tidyUpRobotId);
    }

    public TidyUpRobot findById(UUID tidyUpRobotId) {
        return tidyUpRobots.get(tidyUpRobotId);
    }
    public List<Walkable> findByRoomIdAsWalkables(UUID roomId) {
        List<Walkable> newList = new ArrayList<>();

        tidyUpRobots.forEach((key, value) -> {
            if(value.getRoomId() != null && value.getRoomId().equals(roomId)) {
                newList.add(value);
            }
        });

        return newList;
    }

    public TidyUpRobotRepo() {
        this.tidyUpRobots = new HashMap<>();
    }
}
