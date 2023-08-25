package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckManager {
    private final ArrayList<SpaceShipDeck> spaceShipDecks;

    public DeckManager() {
        this(new ArrayList<>());
    }

    public DeckManager(ArrayList<SpaceShipDeck> droids) {
        this.spaceShipDecks = droids;
    }

    public UUID addDeck (Integer height, Integer width) {
        SpaceShipDeck newDeck = new SpaceShipDeck(width, height);
        spaceShipDecks.add(newDeck);
        return newDeck.getUuid();
    }

    public UUID addConnectionToDeckByUUID (UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        Connection newConnection = new Connection(sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        SpaceShipDeck deck = this.getSpaceShipDeckByUUID(sourceSpaceShipDeckId);
        deck.addConnection(newConnection);
        return newConnection.getUUID();
    }

    public void addBarrierToDeckByUUID (UUID spaceShipDeckId, String barrierString) {
        SpaceShipDeck deck = this.getSpaceShipDeckByUUID(spaceShipDeckId);
        deck.addBarrier(createBarrierFromString(barrierString));
    }

    private Barrier createBarrierFromString(String barrierString) {
        Coordinate startCoordinate = new Coordinate();
        Coordinate endCoordinate = new Coordinate();

        // Generate start and end coordinate values from String with layout (x,y)-(x,y)
        Pattern barrierStringPattern = Pattern.compile("\\((\\d*),(\\d*)\\)-\\((\\d*),(\\d*)");
        Matcher barrierStringMatcher = barrierStringPattern.matcher(barrierString);
        while (barrierStringMatcher.find()) {
            startCoordinate.setX(Integer.parseInt(barrierStringMatcher.group(1)));
            startCoordinate.setY(Integer.parseInt(barrierStringMatcher.group(2)));
            endCoordinate.setX(Integer.parseInt(barrierStringMatcher.group(3)));
            endCoordinate.setY(Integer.parseInt(barrierStringMatcher.group(4)));
        }

        return new Barrier(startCoordinate, endCoordinate);
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
