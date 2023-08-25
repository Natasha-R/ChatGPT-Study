package thkoeln.st.st2praktikum.exercise.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection {
    @Id
    private UUID connectionId;
    @ManyToOne
    @JoinColumn(name = "SourceSpace_ID")
    private Space sourceSpace;
    @ManyToOne
    @JoinColumn(name = "DestinationSpace_ID")
    private Space destinationSpace;
    @ManyToOne
    @JoinColumn(name = "TransportCategory_ID")
    private TransportCategory transportCategory;

    private String sourceCoordinates;
    private String destinationCoordinates;


    public Connection(UUID connectionId, Space sourceSpace, Space destinationSpace, Coordinate sourceCoordinates, Coordinate destinationCoordinates) {
        this.connectionId = connectionId;
        this.sourceSpace = sourceSpace;
        this.destinationSpace = destinationSpace;
        this.sourceCoordinates = sourceCoordinates.toString();
        this.destinationCoordinates = destinationCoordinates.toString();
    }

    public Coordinate getSourceCoordinates() {
        String[] coordinatesSplit = sourceCoordinates.split(",");

        Integer locationX = Integer.parseInt(coordinatesSplit[0].substring(1));
        Integer locationY = Integer.parseInt(coordinatesSplit[1].substring(0,coordinatesSplit[1].length() - 1));
        Coordinate coordinate = new Coordinate(locationX,locationY);

        return coordinate;
    }

    public String getStringSourceCoordinates() {
        return this.sourceCoordinates;
    }

    public Coordinate getDestinationCoordinates() {
        String[] coordinatesSplit = destinationCoordinates.split(",");

        Integer locationX = Integer.parseInt(coordinatesSplit[0].substring(1));
        Integer locationY = Integer.parseInt(coordinatesSplit[1].substring(0,coordinatesSplit[1].length() - 1));
        Coordinate coordinate = new Coordinate(locationX,locationY);

        return coordinate;
    }

    public Space getSourceSpace() {
        return this.sourceSpace;
    }

    public Space getDestinationSpace() {
        return this.destinationSpace;
    }

    public UUID getConnectionId() { return this.connectionId; }

}
