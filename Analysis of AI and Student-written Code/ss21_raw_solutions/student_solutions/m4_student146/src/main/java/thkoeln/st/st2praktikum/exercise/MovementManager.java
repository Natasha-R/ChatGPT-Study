package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public class MovementManager {
    private final WallManager wallManager;

    public MovementManager(WallManager wallManager) {
        this.wallManager = wallManager;
    }

    public MiningMachine moveY(MiningMachine machine, Field field, Order order) {
        try {
            int x = machine.getCoordinate().getX();
            int y = machine.getCoordinate().getY();
            switch (order.getOrderType()) {
                case NORTH:
                    y = wallManager.calculateNorth(field, order.getNumberOfSteps(), x, y);
                    break;
                case SOUTH:
                    y = wallManager.calculateSouth(field, order.getNumberOfSteps(), x, y);
                    break;
            }
            if(y > field.getStart() - 1)
                y = field.getStart() - 1;
            if(y < 0)
                y = 0;
            machine.setCoordinate(new Coordinate(x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machine;
    }

    public MiningMachine moveX(MiningMachine machine, Field field, Order order) {
        try {
            int x = machine.getCoordinate().getX();
            int y = machine.getCoordinate().getY();
            switch (order.getOrderType()) {
                case EAST:
                    x = wallManager.calculateEast(field, order.getNumberOfSteps(), x, y);
                    break;
                case WEST:
                    x = wallManager.calculateWest(field, order.getNumberOfSteps(), x, y);
                    break;
            }
            if(x > field.getEnd() - 1)
                x = field.getEnd() - 1;
            if(x < 0)
                x = 0;
            machine.setCoordinate(new Coordinate(x, y));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machine;
    }
}
