package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Square {
    @Id
    @Generated
    private UUID id;
    private boolean northWall, eastWall, southWall, westWall;
    private UUID tidyUpRobotId;

    @Transient
    private Connection connection;

    public Square(){
        id = UUID.randomUUID();

        northWall = false;
        eastWall = false;
        southWall = false;
        westWall = false;
    }

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
