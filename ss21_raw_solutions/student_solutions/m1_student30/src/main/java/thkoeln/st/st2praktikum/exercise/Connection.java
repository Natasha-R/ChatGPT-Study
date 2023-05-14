package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {


    private UUID entranceShipDeckID;
    private String entranceCoordinates;
    private UUID exitShipDeckID;
    private String exitCoordinates;

    public Connection(UUID entranceShipDeckID, String entranceCoordinates,UUID exitShipDeckID, String exitCoordinates){
        this.entranceShipDeckID=entranceShipDeckID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitShipDeckID=exitShipDeckID;
        this.exitCoordinates=exitCoordinates;
    }

    public String getEntranceCoordinates() {
        return entranceCoordinates;
    }

    public String getExitCoordinates() {
        return exitCoordinates;
    }

    public UUID getEntranceShipDeckID() {
        return entranceShipDeckID;
    }

    public UUID getExitShipDeckID() {
        return exitShipDeckID;
    }
}
