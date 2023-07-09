package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements IConnection {
    private UUID id;
    private ISpaceShipDeck sourceSpaceShipDeck;
    private String sourceCoordinate;
    private ISpaceShipDeck destinationSpaceShipDeck;
    private String destinationCoordinate;

    public Connection(UUID id, ISpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate,
                      ISpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate) {
        this.id = id;
        this.sourceSpaceShipDeck = sourceSpaceShipDeck;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeck = destinationSpaceShipDeck;
        this.destinationCoordinate = destinationCoordinate;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public ISpaceShipDeck getSourceSpaceShipDeck() {
        return sourceSpaceShipDeck;
    }

    @Override
    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    @Override
    public ISpaceShipDeck getDestinationSpaceShipDeck() {
        return destinationSpaceShipDeck;
    }

    @Override
    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public static IConnection createConnection(ISpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate,
                                               ISpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate) {
        UUID id = UUID.randomUUID(); // Generate a new UUID for the connection
        return new Connection(id, sourceSpaceShipDeck, sourceCoordinate, destinationSpaceShipDeck, destinationCoordinate);
    }
}
