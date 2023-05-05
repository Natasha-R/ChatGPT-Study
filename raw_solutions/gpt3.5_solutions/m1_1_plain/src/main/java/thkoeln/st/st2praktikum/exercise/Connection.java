package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID id;
    private SpaceShipDeck sourceSpaceShipDeck;
    private String sourceCoordinate;
    private SpaceShipDeck destinationSpaceShipDeck;
    private String destinationCoordinate;

    public Connection(UUID id, SpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate,
                      SpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate) {
        this.id = id;
        this.sourceSpaceShipDeck = sourceSpaceShipDeck;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeck = destinationSpaceShipDeck;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getId() {
        return id;
    }

    public SpaceShipDeck getSourceSpaceShipDeck() {
        return sourceSpaceShipDeck;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public SpaceShipDeck getDestinationSpaceShipDeck() {
        return destinationSpaceShipDeck;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    // Method to parse connectionString and create Connection object
    public static Connection parseConnection(SpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate,
                                             SpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the connection
        return new Connection(id, sourceSpaceShipDeck, sourceCoordinate, destinationSpaceShipDeck, destinationCoordinate);
    }
}