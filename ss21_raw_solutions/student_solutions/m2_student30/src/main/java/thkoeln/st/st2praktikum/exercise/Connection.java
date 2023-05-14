package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Connection {


    private UUID entranceShipDeckID;
    private Coordinate entranceCoordinates;
    private UUID exitShipDeckID;
    private Coordinate exitCoordinates;

    public Connection(UUID entranceShipDeckID, Coordinate entranceCoordinates,UUID exitShipDeckID, Coordinate exitCoordinates){
        this.entranceShipDeckID=entranceShipDeckID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitShipDeckID=exitShipDeckID;
        this.exitCoordinates=exitCoordinates;
    }



}
