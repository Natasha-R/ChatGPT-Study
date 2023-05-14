package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DroidManager {
    private final ArrayList<MaintenanceDroid> maintenanceDroids;
    private final Boolean verboseOutput = true;
    private final DeckManager deckManager;
    private final DroidMovementService movementService = new DroidMovementService();


    public DroidManager(DeckManager deckManager) {
        this(new ArrayList<>(), deckManager);
    }

    public DroidManager(ArrayList<MaintenanceDroid> droids, DeckManager deckManager) {
        this.maintenanceDroids = droids;
        this.deckManager = deckManager;
    }

    public Boolean executeRequestedCommandOnDroid(UUID maintenanceDroidId, Task task) {
        MaintenanceDroid droid = this.getMaintenanceDroidByUUID(maintenanceDroidId);

        switch (task.getTaskType()) {
            case TRANSPORT:
                return this.useConnection(droid, task.getGridId());
            case ENTER:
                return this.spawnDroidOnDeck(droid, task.getGridId());
            default:
                return this.moveDroid(droid, task);
        }
    }

    public UUID addDroid(String name) {
        MaintenanceDroid newDroid = new MaintenanceDroid(name);
        this.maintenanceDroids.add(newDroid);
        return newDroid.getUuid();
    }

    public boolean moveDroid(MaintenanceDroid droid, Task task) {
        return this.movementService.moveDroid(droid, task, this.deckManager, this.verboseOutput);
    }

    public Boolean useConnection(MaintenanceDroid droid, UUID newDeckId) {
        ArrayList<Connection> deckConnections = deckManager.getSpaceShipDeckByUUID(droid.getCurrentDeckUuid()).getConnections();

        for (Connection connection : deckConnections) {
            if (connection.getSourceCoordinate().equals(droid.getPosition()) && connection.getDestinationSpaceShipDeckId().equals(newDeckId)) {
                return this.traverseConnection(droid, connection);
            }
        }

        return false;
    }

    private Boolean traverseConnection(MaintenanceDroid droid, Connection connection) {
        SpaceShipDeck newDeck = deckManager.getSpaceShipDeckByUUID(connection.getDestinationSpaceShipDeckId());

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is traversing a connection to " + newDeck.getUuid());
        }

        return this.spawnDroidOnDeck(droid, newDeck, connection.getDestinationCoordinate());
    }

    public Boolean spawnDroidOnDeck(MaintenanceDroid droid, UUID deckId) {
        SpaceShipDeck deck = this.deckManager.getSpaceShipDeckByUUID(deckId);
        return this.spawnDroidOnDeck(droid, deck, new Point(0, 0));
    }

    public Boolean spawnDroidOnDeck(MaintenanceDroid droid, SpaceShipDeck deck, Point position) {
        if (!this.checkIfCurrentSpaceOnDeckIsFree(deck, position)) {
            if (verboseOutput) {
                System.out.println("Droid " + droid.getName() +
                        " cant traverse to deck " + deck.getUuid() +
                        " because point " + position + " is not free");
                System.out.println("--------");
            }
            return false;
        }

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is spawning on deck " + deck.getUuid() + " with Position " + position);
            System.out.println("--------");
        }

        droid.spawnOnDeck(deck.getUuid(), position);

        return true;
    }

    private Boolean checkIfCurrentSpaceOnDeckIsFree(SpaceShipDeck deck, Point point) {
        ArrayList<MaintenanceDroid> droids = this.getMaintenanceDroidsByDeckId(deck.getUuid());
        for (MaintenanceDroid droid : droids) {
            if (droid.getPosition().equals(point)) return false;
        }

        return true;
    }

    public ArrayList<MaintenanceDroid> getMaintenanceDroidsByDeckId(UUID deckId) {
        if (this.maintenanceDroids.isEmpty()) return new ArrayList<>();
        return this.maintenanceDroids.stream().filter(b -> b.getCurrentDeckUuid() != null)
                .filter(b -> b.getCurrentDeckUuid().equals(deckId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public MaintenanceDroid getMaintenanceDroidByUUID(UUID maintenanceDroidId) {
        Optional<MaintenanceDroid> droid = this.maintenanceDroids.stream().filter(b -> b.getUuid().equals(maintenanceDroidId)).findFirst();
        if (droid.isEmpty()) throw new InvalidParameterException("No Droid Matches the given UUID");
        return droid.get();
    }
}
