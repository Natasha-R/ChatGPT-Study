package thkoeln.st.st2praktikum.exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Square {
    private boolean northWall, eastWall, southWall, westWall;
    private UUID tidyUpRobotId;
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

    public void addTidyUpRobotId(UUID tidyUpRobotId) {
        this.tidyUpRobotId = tidyUpRobotId;
    }

    public void removeTidyUpRobotId() {
        tidyUpRobotId = null;
    }

    public Boolean isOccupied() {
        return tidyUpRobotId != null;
    }
}
