package thkoeln.st.st2praktikum.exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Square {
    private boolean northObstacle, eastObstacle, southObstacle, westObstacle;
    private UUID cleaningDeviceId;
    private Connection connection;

    public void addObstacle(String wall) {
        switch (wall) {
            case "no":
                northObstacle = true;
                break;
            case "ea":
                eastObstacle = true;
                break;
            case "so":
                southObstacle = true;
                break;
            case "we":
                westObstacle = true;
        }
    }

    public void addConnection(Connection connection) {
        this.connection = connection;
    }

    public void addCleaningDeviceId(UUID cleaningDeviceId) {
        this.cleaningDeviceId = cleaningDeviceId;
    }

    public void removeCleaningDeviceId() {
        cleaningDeviceId = null;
    }

    public Boolean isOccupied() {
        return cleaningDeviceId != null;
    }
}
