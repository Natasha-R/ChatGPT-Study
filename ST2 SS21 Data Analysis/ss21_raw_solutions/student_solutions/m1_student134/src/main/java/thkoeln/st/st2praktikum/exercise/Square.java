package thkoeln.st.st2praktikum.exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Square {
    private boolean northWall, eastWall, southWall, westWall;
    private UUID cleaningDeviceId;
    private Connection connection;

    public void addWall(String wall) {
        switch (wall) {
            case "no":
                northWall = true;
                break;
            case "ea":
                eastWall = true;
                break;
            case "so":
                southWall = true;
                break;
            case "we":
                westWall = true;
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
