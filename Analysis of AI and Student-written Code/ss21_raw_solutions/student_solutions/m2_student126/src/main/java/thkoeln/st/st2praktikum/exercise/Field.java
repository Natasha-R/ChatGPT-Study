package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Grid;

import java.util.*;

@NoArgsConstructor
@Getter
public class Field extends AbstractEntity implements Grid {

    @Setter
    private int width;
    @Setter
    private int height;
    private final List<Barrier> barrierList = new ArrayList<>();
    private final Map<UUID, MiningMachine> miningMachineMap = new HashMap<>();


    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addBarrier(Barrier barrier) {
        barrierList.add(barrier);
    }

    public void addMiningMachine(MiningMachine miningMachine, Coordinate coordinate) throws Exception {
        if (isCellOccupied(coordinate))
            throw new Exception("The Field is already occupied");
        miningMachine.setCoordinate(coordinate);
        miningMachineMap.put(miningMachine.getId(), miningMachine);

    }

    public void removeMiningMachine(UUID miningMachineId) {
        miningMachineMap.remove(miningMachineId);
    }

    public void moveMiningMachine(UUID miningMachineID, CommandType direction, int distance) {
        MiningMachine miningMachine = miningMachineMap.get(miningMachineID);
        if (miningMachine == null) {
            throw new ResourceNotFoundException("No Mining Machine with this id");
        }
        for (int i = 0; i < distance; i++) {
            Coordinate futureCoordinate = getFutureCoordinate(direction, miningMachine.getCoordinate());
            if(futureCoordinate.equals(miningMachine.getCoordinate()))
                break;
            if (willCollide(futureCoordinate, miningMachine))
                break;
            moveMiningMachineInDirection(miningMachine, direction);
        }
    }

    private void moveMiningMachineInDirection(MiningMachine miningMachine, CommandType direction) {
        switch (direction) {
            case NORTH:
                miningMachine.move(CommandType.NORTH);
                break;
            case EAST:
                miningMachine.move(CommandType.EAST);
                break;
            case SOUTH:
                miningMachine.move(CommandType.SOUTH);
                break;
            case WEST:
                miningMachine.move(CommandType.WEST);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean willCollide(Coordinate futureCoordinate, MiningMachine miningMachine) {
        if (futureCoordinate.getX() >= width || futureCoordinate.getY() >= height)
            return true;
        if (willCrossBarrier(miningMachine.getCoordinate(), futureCoordinate))
            return true;
        if (isCellOccupied(futureCoordinate))
            return true;
        return false;
    }

    private Coordinate getFutureCoordinate(CommandType direction, Coordinate currentCoordinate) {
        switch (direction) {
            case NORTH:
                return new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() + 1);
            case SOUTH:
                int y = currentCoordinate.getY() - 1;
                if (y < 0)
                    return currentCoordinate;
                else
                    return new Coordinate(currentCoordinate.getX(), y);
            case EAST:
                return new Coordinate(currentCoordinate.getX() + 1, currentCoordinate.getY());
            case WEST:
                int x = currentCoordinate.getX() - 1;
                if (x < 0)
                    return currentCoordinate;
                else
                    return new Coordinate(x, currentCoordinate.getY());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isCellOccupied(Coordinate coordinate) {
        for (MiningMachine m : miningMachineMap.values()) {
            if (m.isBlocked(coordinate, coordinate))
                return true;
        }
        return false;
    }

    private boolean willCrossBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        for (Barrier b : barrierList) {
            if (b.isBlocked(currentCoordinate, futureCoordinate))
                return true;
        }
        return false;
    }

}
