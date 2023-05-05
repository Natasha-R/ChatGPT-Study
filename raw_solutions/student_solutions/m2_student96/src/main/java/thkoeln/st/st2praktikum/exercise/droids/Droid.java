package thkoeln.st.st2praktikum.exercise.droids;

import lombok.Data;

import thkoeln.st.st2praktikum.exercise.control.Controller;
import thkoeln.st.st2praktikum.exercise.converter.StringConverter;
import thkoeln.st.st2praktikum.exercise.map.Grid;

import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.map.PointException;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.OrderType;


import java.util.UUID;

@Data
public class Droid {
    private UUID droidID;
    private Point position;
    private String name;
    private StringConverter convert = new StringConverter();

    public Droid(String name) {
        this.setName(name);
        this.setDroidID(UUID.randomUUID());
    }

    public boolean spawnDroid(Grid destinationGrid) {

        Point destination = destinationGrid.getPointFromGrid("(0,0)");
        if (destination.isBlocked()) {
            return false;
        }
        destination.setIsBlocked(true);
        this.setPosition(destination);
        return true;
    }

    public boolean transportDroid(Grid destinationDeck) {
        try {
            Point start = getPosition();
            Point destination = getPosition().getConnections().get(OrderType.TRANSPORT);
            if (destinationDeck.getPoints().containsKey(destination.toString()) && !destination.isBlocked()) {
                destination.setIsBlocked(true);
                setPosition(destination);
                start.setIsBlocked(false);
            }
        } catch (NullPointerException e) {
            System.out.println("Exception:" + e);
            return false;
        }
        return false;

    }

    public boolean executeOrder(Order order, Controller controller) {
        switch ( order.getOrderType() ) {
            case ENTER:
                return spawnDroid( controller.getDeckByID( order.getGridId() ) );
            case TRANSPORT:
                return transportDroid( controller.getDeckByID( order.getGridId() ) );
            case NORTH:
                return stepNorth( order.getNumberOfSteps() );
            case SOUTH:
                return stepSouth( order.getNumberOfSteps() );
            case EAST:
                return stepEast( order.getNumberOfSteps() );
            case WEST:
                return stepWest( order.getNumberOfSteps() );
            default:
                return false;
        }
    }

    public boolean stepNorth(int steps) {
        if (steps == 0) {
            return true;
        }
        try {
            if (position.getNorth().isBlocked()) {
                return true;
            } else {
                position.getNorth().setIsBlocked(true);
            }
        } catch (PointException e) {
            return true;
        }
        position = position.getNorth();
        position.getSouth().setIsBlocked(false);
        return stepNorth(steps - 1);
    }

    public boolean stepSouth(int steps) {
        if (steps == 0) {
            return true;
        }
        try {
            if (position.getSouth().isBlocked()) {
                return true;
            } else {
                position.getSouth().setIsBlocked(true);
                position = position.getSouth();
                position.getNorth().setIsBlocked(false);
                return stepSouth(steps - 1);
            }
        } catch (PointException e) {
            return true;
        }
    }

    public boolean stepEast(int steps) {
        Point start = position;
        if (steps == 0) {
            return true;
        }
        try {
            if (position.getEast().isBlocked()) {
                return true;
            } else {
                start.getEast().setIsBlocked(true);
                this.setPosition(start.getEast());
                start.setIsBlocked(false);
                return stepEast(steps - 1);
            }
        } catch (PointException e) {
            return true;
        }
    }

    public boolean stepWest(int steps) {
        if (steps == 0) {
            return true;
        }
        try {
            if (position.getWest().isBlocked()) {
                return true;
            } else {
                position.getWest().setIsBlocked(true);
                position = position.getWest();
                position.getEast().setIsBlocked(false);
                return stepWest(steps - 1);
            }
        } catch (PointException e) {
            return true;
        }
    }

    public String toString() {
        return getName();
    }
}