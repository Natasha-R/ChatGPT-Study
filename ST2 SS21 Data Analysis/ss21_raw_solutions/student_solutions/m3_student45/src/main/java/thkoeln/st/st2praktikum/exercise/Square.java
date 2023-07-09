package thkoeln.st.st2praktikum.exercise;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Connection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Square {
    @Id
    @Generated
    private UUID id;
    private boolean northWall, eastWall, southWall, westWall;
    private UUID tidyUpRobotId;

    @ManyToOne
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
