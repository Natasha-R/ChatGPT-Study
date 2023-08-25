package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Square {
    private boolean northBarrier;
    private boolean eastBarrier;
    private boolean southBarrier;
    private boolean westBarrier;
    private UUID tidyUpRobotId;
    private Connection connection;

    public void addBarrier(String barrierString){
        switch(barrierString) {
            case "no":
                this.northBarrier = true;
                break;
            case "ea":
                this.eastBarrier = true;
                break;
            case "so":
                this.southBarrier = true;
                break;
            case "we":
                this.westBarrier = true;
        }
    }
    public void addTidyUpRobotId(UUID tidyUpRobotId) {
        this.tidyUpRobotId = tidyUpRobotId;
    }

    public void removeTidyUpRobotId() {
        this.tidyUpRobotId = null;
    }

    public Boolean isOccupied() {
        return this.tidyUpRobotId != null;
    }

    public boolean isNorthBarrier() {
        return this.northBarrier;
    }

    public boolean isEastBarrier() {
        return this.eastBarrier;
    }

    public boolean isSouthBarrier() {
        return this.southBarrier;
    }

    public boolean isWestBarrier() {
        return this.westBarrier;
    }

    public void addConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }


}
