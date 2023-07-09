package thkoeln.st.st2praktikum.exercise;

import lombok.Data;

import java.util.UUID;

@Data
public class Connection {
    private UUID uuid;
    private UUID sourceID;
    private UUID destinationID;
    private Coordinate  source;
    private Coordinate destination;

    public Connection (UUID sourceID, String source, UUID destinationID, String destination){
        uuid = UUID.randomUUID();
        this.source = Coordinate.switchStringToCoordinate(source);
        this.destination = Coordinate.switchStringToCoordinate(destination);
        this.sourceID = sourceID;
        this.destinationID = destinationID;
    }

}
