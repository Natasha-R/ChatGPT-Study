package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CoordinatesForRobots {
    public HashMap<UUID, int[]> getCoordinatesForRobots() {
        return coordinatesForRobots;
    }

    private HashMap<UUID, int[]> coordinatesForRobots = new HashMap<>();

    public CoordinatesForRobots() {
        this.coordinatesForRobots = coordinatesForRobots;
    }

    public void add(UUID roomUUID, int[] coordinates) {
        coordinatesForRobots.put(roomUUID, coordinates);
    }

}

