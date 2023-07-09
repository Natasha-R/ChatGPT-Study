package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class DeckManager {
    private final ArrayList<SpaceShipDeck> spaceShipDecks;

    public DeckManager() {
        this(new ArrayList<>());
    }

    public DeckManager(ArrayList<SpaceShipDeck> droids) {
        this.spaceShipDecks = droids;
    }

    public UUID addDeck(Integer height, Integer width) {
        SpaceShipDeck newDeck = new SpaceShipDeck(width, height);
        spaceShipDecks.add(newDeck);
        return newDeck.getUuid();
    }

    public UUID addConnectionToDeckByUUID(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        Connection newConnection = new Connection(sourcePoint, destinationSpaceShipDeckId, destinationPoint);
        SpaceShipDeck deck = this.getSpaceShipDeckByUUID(sourceSpaceShipDeckId);
        deck.addConnection(newConnection);
        return newConnection.getUUID();
    }

    public void addBarrierToDeckByUUID(UUID spaceShipDeckId, Barrier barrier) {
        SpaceShipDeck deck = this.getSpaceShipDeckByUUID(spaceShipDeckId);
        deck.addBarrier(barrier);
    }

    public SpaceShipDeck getSpaceShipDeckByUUID(UUID spaceShipDeckId) {
        Optional<SpaceShipDeck> deck = this.spaceShipDecks.stream().filter(b -> b.getUuid().equals(spaceShipDeckId)).findFirst();
        if (deck.isEmpty()) throw new InvalidParameterException("No Deck Matches the given UUID");
        return deck.get();
    }

    public ArrayList<SpaceShipDeck> getSpaceShipDecks() {
        return spaceShipDecks;
    }
}
