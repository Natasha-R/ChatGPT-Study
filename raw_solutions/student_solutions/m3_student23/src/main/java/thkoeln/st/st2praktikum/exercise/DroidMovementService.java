package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiConsumer;

import static thkoeln.st.st2praktikum.exercise.TaskType.*;

public class DroidMovementService {

    private Boolean verboseOutput;
    private DeckManager deckManager;

    public Boolean moveDroid(MaintenanceDroid droid, Task task, DeckManager deckManager, Boolean verboseOutput) {
        this.deckManager = deckManager;
        this.verboseOutput = verboseOutput;

        if (this.verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is moving with command " + task);
            System.out.println("Start Position: (" + droid.getPoint().getX() + "," + droid.getPoint().getY() + ")");
        }

        // We use a temporary position while calculating the new position because we are ignoring barriers when moving
        // and correcting them after the fact by clamping the position to the nearest barrier / the grid walls.
        Point tempPosition = droid.getPoint();

        BiConsumer<Barrier, Point> clampingStrategy = this.performMovement(task.getTaskType(), task.getNumberOfSteps(), tempPosition);
        String movementAxis = getMovementAxis(task.getTaskType());
        this.clampPosition(movementAxis, clampingStrategy, droid, tempPosition);

        droid.moveTo(tempPosition);

        if (this.verboseOutput) {
            System.out.println("End Position: (" + droid.getPoint().getX() + "," + droid.getPoint().getY() + ")");
            System.out.println("Deck: " + droid.getSpaceShipDeckId());
            System.out.println("--------");
        }

        return true;
    }

    private BiConsumer<Barrier, Point> performMovement(TaskType direction, int amount, Point tempPosition) {

        switch (direction) {
            case NORTH: {
                tempPosition.incrementY(amount);
                return this::clampPositiveX;
            }
            case EAST: {
                tempPosition.incrementX(amount);
                return this::clampPositiveY;
            }
            case SOUTH: {
                tempPosition.decrementY(amount);
                return this::clampNegativeX;
            }
            case WEST: {
                tempPosition.decrementX(amount);
                return this::clampNegativeY;
            }
            default:
                throw new InvalidParameterException("Invalid direction given.");
        }
    }

    private String getMovementAxis(TaskType direction) {
        if (direction.equals(NORTH) || direction.equals(SOUTH)) return "y";
        if (direction.equals(EAST) || direction.equals(WEST)) return "x";
        throw new InvalidParameterException("Invalid direction given");
    }

    private void clampPosition(String barrierAxis, BiConsumer<Barrier, Point> clampingStrategy, MaintenanceDroid droid, Point tempPosition) {
        // TODO: also check for other droids on the same deck
        this.clampPositionToBarrier(this.getFirstRelevantBarrier(barrierAxis, droid, tempPosition), clampingStrategy, tempPosition);
        this.clampPositionToGrid(droid, tempPosition);
    }

    private void clampPositionToGrid(MaintenanceDroid droid, Point tempPosition) {
        SpaceShipDeck currentDeck = this.getCurrentDeckForDroid(droid);

        if (this.verboseOutput) {
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
    private void clampPositionToBarrier(Barrier barrier, BiConsumer<Barrier, Point> clampingStrategy, Point tempPosition) {
        if (this.verboseOutput) {
            System.out.println("Position before Clamp: " + tempPosition);
            System.out.println("Relevant barrier: " + barrier);
        }

        if (barrier != null) {
            clampingStrategy.accept(barrier, tempPosition);
        }

        if (this.verboseOutput) {
            System.out.println("Position after Barrier Clamp: " + tempPosition);
        }
    }

    private void clampPositiveX(Barrier barrier, Point tempPosition) {
        if (this.positiveYBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY() - 1);
    }

    private void clampPositiveY(Barrier barrier, Point tempPosition) {
        if (this.positiveXBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY() - 1);
    }

    private void clampNegativeX(Barrier barrier, Point tempPosition) {
        if (this.negativeXBarrierInTheWay(barrier, tempPosition)) tempPosition.setX(barrier.getStart().getX());
    }

    private void clampNegativeY(Barrier barrier, Point tempPosition) {
        if (this.negativeYBarrierInTheWay(barrier, tempPosition)) tempPosition.setY(barrier.getStart().getY());
    }

    private Boolean positiveXBarrierInTheWay(Barrier barrier, Point tempPosition) {
        return barrier.getStart().getX() <= tempPosition.getX();
    }

    private Boolean positiveYBarrierInTheWay(Barrier barrier, Point tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY();
    }

    private Boolean negativeXBarrierInTheWay(Barrier barrier, Point tempPosition) {
        return barrier.getStart().getX() >= tempPosition.getX();
    }

    private Boolean negativeYBarrierInTheWay(Barrier barrier, Point tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY();
    }

    /**
     * @return get the nearest Barrier that is relevant to the next movement
     */
    private Barrier getFirstRelevantBarrier(String axis, MaintenanceDroid droid, Point tempPosition) {
        Barrier[] relevantBarriers = getRelevantBarriers(axis, droid, tempPosition);
        return relevantBarriers.length > 0 ? relevantBarriers[0] : null;
    }

    /**
     * @return Array with all barriers that lie on the given axis and that are reachable by a straight move along the axis
     */
    private Barrier[] getRelevantBarriers(String movementAxis, MaintenanceDroid droid, Point tempPosition) {
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

    private Boolean positionBetweenStartAndEndOfYBarrier(Barrier barrier, Point tempPosition) {
        return barrier.getStart().getY() <= tempPosition.getY() && barrier.getEnd().getY() >= tempPosition.getY() + 1;
    }

    private Boolean positionBetweenStartAndEndOfXBarrier(Barrier barrier, Point tempPosition) {
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

    private SpaceShipDeck getCurrentDeckForDroid(MaintenanceDroid droid) {
        return deckManager.getSpaceShipDeckByUUID(droid.getSpaceShipDeckId());
    }

}
