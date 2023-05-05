package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class Space implements Createable {

    public static HashMap<UUID, SpaceShipDeck> decks = new HashMap<>();
    public static HashMap<UUID, MaintenanceDroid> maintenanceDroids = new HashMap<>();
    public static HashMap<UUID, Connection> connections = new HashMap<>();
    public static SpaceShipDeck fetchSpaceShipDeckId(UUID deckId){
        return decks.get(deckId);
    }

    @Override
    public UUID createSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck deck = new SpaceShipDeck();
        deck.setWidth(width);
        deck.setHeight(height);

        decks.put(deck.getId(), deck);

        return deck.getId();
    }

    @Override
    public void createBarrier(UUID deck, String barrierString) {
        fetchSpaceShipDeckId(deck).addBarrier(barrierString);
    }

    @Override
    public UUID createConnection(UUID sourceSpaceShipDeckId, Point sourceCoordinate,
                                 UUID destinationSpaceShipDeckId, Point destinationCoordinate) {
        Connection connection =  new Connection();

        connection.setSourceSpaceShipDeckId(sourceSpaceShipDeckId);
        connection.setDestinationSpaceShipDeckId(destinationSpaceShipDeckId);
        connection.setSourceCoordinate(sourceCoordinate);
        connection.setDestinationCoordinate(destinationCoordinate);

        decks.get(sourceSpaceShipDeckId).connections.add(connection);
        connections.put(connection.getId(), connection);

        return connection.getId();
    }

    @Override
    public UUID createMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.setName(name);
        maintenanceDroids.put(maintenanceDroid.getId(),maintenanceDroid);

        return maintenanceDroid.getId();
    }

    protected UUID getMDSpaceShipDeckId(UUID maintenanceDroid){
        return maintenanceDroids.get(maintenanceDroid).getCurrentSpaceShipDeckId();
    }

    public static boolean initialise(UUID maintenanceDroid, String command) {
        UUID deck = UUID.fromString(command.substring(command.indexOf(',') + 1, command.length() - 1));

        if (decks.get(deck).maintenanceDroids.isEmpty()) {
            maintenanceDroids.get(maintenanceDroid).setCurrentSpaceShipDeckId(deck);
            maintenanceDroids.get(maintenanceDroid).setCurrentSpaceShipDeck(decks.get(deck));
            decks.get(deck).maintenanceDroids.add(maintenanceDroids.get(maintenanceDroid));
            return true;
        }else{
            for (int i = 0; i < decks.get(deck).maintenanceDroids.size();i++) {
                if (decks.get(deck).maintenanceDroids.get(i).coordinate.getX() == 0 &&
                        decks.get(deck).maintenanceDroids.get(i).coordinate.getY() == 0) {
                    return false;
                } else {
                    maintenanceDroids.get(maintenanceDroid).setCurrentSpaceShipDeckId(deck);
                    decks.get(deck).maintenanceDroids.add(maintenanceDroids.get(maintenanceDroid));
                    maintenanceDroids.get(maintenanceDroid).setCurrentSpaceShipDeck(decks.get(deck));
                    return true;
                }
            }
        }
        return false;
    }

}