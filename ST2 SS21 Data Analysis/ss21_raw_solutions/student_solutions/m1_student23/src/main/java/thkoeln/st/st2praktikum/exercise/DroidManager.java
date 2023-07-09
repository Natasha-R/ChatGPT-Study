package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DroidManager {
    private final ArrayList<MaintenanceDroid> maintenanceDroids;
    private final Boolean verboseOutput = true;
    private final DeckManager deckManager;


    public DroidManager(DeckManager deckManager) {
        this(new ArrayList<>(), deckManager);
    }

    public DroidManager(ArrayList<MaintenanceDroid> droids, DeckManager deckManager) {
        this.maintenanceDroids = droids;
        this.deckManager = deckManager;
    }

    public Boolean executeRequestedCommandOnDroid(UUID maintenanceDroidId, String command, String parameter, String commandString) {
        MaintenanceDroid droid = this.getMaintenanceDroidByUUID(maintenanceDroidId);

        switch (command) {
            case "tr":
                return this.useConnection(droid, UUID.fromString(parameter));
            case "en":
                return this.spawnDroidOnDeck(droid, UUID.fromString(parameter));
            default:
                return this.moveDroid(droid, commandString);
        }
    }

    public UUID addDroid(String name) {
        MaintenanceDroid newDroid = new MaintenanceDroid(name);
        this.maintenanceDroids.add(newDroid);
        return newDroid.getUuid();
    }

    public Boolean moveDroid(MaintenanceDroid droid, String commandString) {
        String direction = "";
        int amount = 0;

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is moving with command " + commandString);
            System.out.println("Start Position: (" + droid.getPosition().getX() + "," + droid.getPosition().getY() + ")");
        }

        // use Regex to parse command String to get direction (e.g. 'ea') and movement amount
        Pattern pattern = Pattern.compile("\\[(.*)(?:,)(.*)]");
        Matcher matcher = pattern.matcher(commandString);
        while (matcher.find()) {
            direction = matcher.group(1);
            amount = Integer.parseInt(matcher.group(2));
        }

        // We use a temporary position while calculating the new position because we are ignoring barriers when moving
        // and correcting them after the fact by clamping the position to the nearest barrier / the grid walls.
        Coordinate tempPosition = droid.getPosition();

        BiConsumer<Barrier, Coordinate> clampingStrategy = this.performMovement(direction, amount, tempPosition);
        String movementAxis = getMovementAxis(direction);
        this.clampPosition(movementAxis, clampingStrategy, droid, tempPosition);

        droid.moveTo(tempPosition);

        if (verboseOutput) {
            System.out.println("End Position: (" + droid.getPosition().getX() + "," + droid.getPosition().getY() + ")");
            System.out.println("Deck: " + droid.getCurrentDeckUuid());
            System.out.println("--------");
        }

        return true;
    }

    private BiConsumer<Barrier, Coordinate> performMovement(String direction, int amount, Coordinate tempPosition) {

        switch (direction) {
            case "no": {
                tempPosition.incrementY(amount);
                return this::clampPositiveX;
            }
            case "ea": {
                tempPosition.incrementX(amount);
                return this::clampPositiveY;
            }
            case "so": {
                tempPosition.decrementY(amount);
                return this::clampNegativeX;
            }
            case "we": {
                tempPosition.decrementX(amount);
                return this::clampNegativeY;
            }
            default:
                throw new InvalidParameterException("Invalid direction given. Must be one of (no, ea, so, we).");
        }
    }

    private String getMovementAxis(String direction) {
        if (direction.equals("no") || direction.equals("so")) return "y";
        if (direction.equals("ea") || direction.equals("we")) return "x";
        throw new InvalidParameterException("Invalid direction given. Must be one of (no, ea, so, we).");
    }

    private void clampPosition(String barrierAxis, BiConsumer<Barrier, Coordinate> clampingStrategy, MaintenanceDroid droid, Coordinate tempPosition) {
        // TODO: also check for other droids on the same deck
        this.clampPositionToBarrier(this.getFirstRelevantBarrier(barrierAxis, droid, tempPosition), clampingStrategy, tempPosition);
        this.clampPositionToGrid(droid, tempPosition);
    }

    private void clampPositionToGrid(MaintenanceDroid droid, Coordinate tempPosition) {
        SpaceShipDeck currentDeck = this.getCurrentDeckForDroid(droid);

        if (verboseOutput) {
            System.out.println("Clamping position to grid with dimensions " + currentDeck.getGridWidth() + "x" + currentDeck.getGridHeight());
        }

        if (tempPosition.getX() > currentDeck.getGridWidth() - 1)
            tempPosition.setX(currentDeck.getGridWidth() - 1);
        if (tempPosition.getY() > currentDeck.getGridHeight() - 1)
            tempPosition.setY(currentDeck.getGridHeight() - 1);
        if (tempPosition.getX() < 0) tempPosition.setX(0);
        if (tempPosition.getY() < 0) tempPosition.setY(0);
    }

    /**
     * Clamp the position to respect barriers
     *
     * @param barrier          the barrier that has to be respected
     * @param clampingStrategy the clamping strategy that will be executed
     */
    private void clampPositionToBarrier(Barrier barrier, BiConsumer<Barrier, Coordinate> clampingStrategy, Coordinate tempPosition) {
        if (verboseOutput) {
            System.out.println("Position before Clamp: " + tempPosition);
            System.out.println("Relevant barrier: " + barrier);
        }

        if (barrier != null) {
            clampingStrategy.accept(barrier, tempPosition);
        }

        if (verboseOutput) {
            System.out.println("Position after Barrier Clamp: " + tempPosition);
        }
    }

    private void clampPositiveX(Barrier barrier, Coordinate tempPosition) {
        if (this.positiveYBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY() - 1);
    }

    private void clampPositiveY(Barrier barrier, Coordinate tempPosition) {
        if (this.positiveXBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY() - 1);
    }

    private void clampNegativeX(Barrier barrier, Coordinate tempPosition) {
        if (this.negativeXBarrierInTheWay(barrier, tempPosition)) tempPosition.setX(barrier.getStart().getX());
    }

    private void clampNegativeY(Barrier barrier, Coordinate tempPosition) {
        if (this.negativeYBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY());
    }

    private Boolean positiveXBarrierInTheWay(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getX() <= tempPosition.getX();
    }

    private Boolean positiveYBarrierInTheWay(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY();
    }

    private Boolean negativeXBarrierInTheWay(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getX() >= tempPosition.getX();
    }

    private Boolean negativeYBarrierInTheWay(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY();
    }

    /**
     * @return get the nearest Barrier that is relevant to the next movement
     */
    private Barrier getFirstRelevantBarrier(String axis, MaintenanceDroid droid, Coordinate tempPosition) {
        Barrier[] relevantBarriers = getRelevantBarriers(axis, droid, tempPosition);
        return relevantBarriers.length > 0 ? relevantBarriers[0] : null;
    }

    /**
     * @return Array with all barriers that lie on the given axis and that are reachable by a straight move along the axis
     */
    private Barrier[] getRelevantBarriers(String movementAxis, MaintenanceDroid droid, Coordinate tempPosition) {
        ArrayList<Barrier> relevantBarriers = new ArrayList<>();

        Barrier[] barriersByAxis = this.getRelevantBarriersByAxis(movementAxis, droid);

        for (Barrier barrier : barriersByAxis) {
            if (this.useXAxis(movementAxis)) {
                if (this.positionBetweenStartAndEndOfYBarrier(barrier, tempPosition)) relevantBarriers.add(barrier);
            } else {
                if (this.positionBetweenStartAndEndOfXBarrier(barrier, tempPosition)) relevantBarriers.add(barrier);
            }
        }

        return relevantBarriers.toArray(Barrier[]::new);
    }

    private Boolean positionBetweenStartAndEndOfYBarrier(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY() && barrier.getEnd().getY() >= tempPosition.getY() + 1;
    }

    private Boolean positionBetweenStartAndEndOfXBarrier(Barrier barrier, Coordinate tempPosition) {
        return barrier.getStart().getX() <= tempPosition.getX() && barrier.getEnd().getX() >= tempPosition.getX() + 1;
    }

    /**
     * @param axis axis in the grid
     * @return Array with all Barriers that lie on the given axis
     */
    private Barrier[] getRelevantBarriersByAxis(String axis, MaintenanceDroid droid) {
        SpaceShipDeck currentDeck = this.getCurrentDeckForDroid(droid);

        Barrier[] relevantBarriers = currentDeck.getBarriers().stream().filter(n -> n.getAxis().equals(axis)).toArray(Barrier[]::new);

        if (this.useXAxis(axis)) {
            Arrays.sort(relevantBarriers, Comparator.comparingInt(b -> b.getStart().getX()));
        } else {
            Arrays.sort(relevantBarriers, Comparator.comparingInt(b -> b.getStart().getY()));
        }

        return relevantBarriers;
    }

    private Boolean useXAxis(String axis) {
        switch (axis) {
            case "x": {
                return true;
            }
            case "y": {
                return false;
            }
            default:
                throw new InvalidParameterException("Invalid Axis provided. Must be one of (x, y).");
        }
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
            System.out.println("--------");
        }

        return droid.spawnOnDeck(newDeck.getUuid(), connection.getDestinationCoordinate());
    }

    public Boolean spawnDroidOnDeck (MaintenanceDroid droid, UUID deckId) {
        SpaceShipDeck deck = this.deckManager.getSpaceShipDeckByUUID(deckId);
        return this.spawnDroidOnDeck(droid, deck, new Coordinate(0, 0));
    }

    public Boolean spawnDroidOnDeck (MaintenanceDroid droid, SpaceShipDeck deck, Coordinate position) {
        if (!this.checkIfCurrentSpaceOnDeckIsFree(deck)) return false;

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is spawning on deck " + deck.getUuid() + " with Position " + position);
            System.out.println("--------");
        }

        droid.spawnOnDeck(deck.getUuid(), position);

        return true;
    }

    private Boolean checkIfCurrentSpaceOnDeckIsFree(SpaceShipDeck deck) {
        ArrayList<MaintenanceDroid> droids = this.getMaintenanceDroidsByDeckId(deck.getUuid());
        for (MaintenanceDroid droid : droids) {
            if (droid.getPosition().equals(new Coordinate(0, 0))) return false;
        }

        return true;
    }

    public SpaceShipDeck getCurrentDeckForDroid (MaintenanceDroid droid) {
        return deckManager.getSpaceShipDeckByUUID(droid.getCurrentDeckUuid());
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
