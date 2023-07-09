package thkoeln.st.st2praktikum.exercise.util;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.MiningMachine;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.Order;

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
            if(y > field.getEnd().getY() - 1)
                y = field.getEnd().getY() - 1;
            if(y < field.getStart().getY())
                y = field.getStart().getY();
            machine = new MiningMachine(new Coordinate(x, y), field.getUuid());
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
            if(x > field.getEnd().getX() - 1)
                x = field.getEnd().getX() - 1;
            if(x < field.getStart().getX())
                x = field.getStart().getX();
            machine = new MiningMachine(new Coordinate(x, y), field.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machine;
    }
}
