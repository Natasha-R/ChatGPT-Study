package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Grid;
import thkoeln.st.st2praktikum.exercise.valueObjects.Barrier;
import thkoeln.st.st2praktikum.exercise.valueObjects.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

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

    public void moveMiningMachineHorizontal(UUID miningMachineID, int distance) {
        MiningMachine miningMachine = miningMachineMap.get(miningMachineID);
        if (miningMachine == null) {
            throw new ResourceNotFoundException("No Mining Machine with this id");
        }
        for (int i = 0; i < Math.abs(distance); i++) {
            int step = distance / Math.abs(distance);
            Coordinate futureCoordinate = new Coordinate(miningMachine.getCoordinate().getX() + step, miningMachine.getCoordinate().getY());
            if (futureCoordinate.getX() < 0 || futureCoordinate.getX() >= width)
                break;
            if (willCrossBarrier(miningMachine.getCoordinate(), futureCoordinate))
                break;
            if (isCellOccupied(futureCoordinate))
                break;
            miningMachine.setCoordinate(futureCoordinate);
        }
    }

    public void moveMiningMachineVertical(UUID miningMachineID, int distance) {
        MiningMachine miningMachine = miningMachineMap.get(miningMachineID);
        if (miningMachine == null) {
            throw new ResourceNotFoundException("No Mining Machine with this id");
        }
        for (int i = 0; i < Math.abs(distance); i++) {
            int step = distance / Math.abs(distance);
            Coordinate futureCoordinate = new Coordinate(miningMachine.getCoordinate().getX(), miningMachine.getCoordinate().getY() + step);
            if (futureCoordinate.getY() < 0 || futureCoordinate.getY() >= height)
                break;
            if (willCrossBarrier(miningMachine.getCoordinate(), futureCoordinate))
                break;
            if (isCellOccupied(futureCoordinate))
                break;
            miningMachine.setCoordinate(futureCoordinate);
        }
    }

    @Override
    public boolean isCellOccupied(Coordinate coordinate) {
        for (MiningMachine m : miningMachineMap.values()) {
            if (m.getCoordinate().equals(coordinate)) {
                return true;
            }
        }
        return false;
    }

    private boolean willCrossBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        if (movesVertically(currentCoordinate, futureCoordinate)) {
            return willCrossVerticalBarrier(currentCoordinate, futureCoordinate);
        } else if (movesHorizontally(currentCoordinate, futureCoordinate)) {
            return willCrossHorizontalBarrier(currentCoordinate, futureCoordinate);
        }
        return false;
    }

    private boolean willCrossHorizontalBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        List<Barrier> barriers = getHorizontalBarriers();
        for (Barrier b : barriers) {
            if (b.getBarrierStartCoordinate().getY() <= currentCoordinate.getY()
                    && b.getBarrierEndCoordinate().getY() > currentCoordinate.getY()) {
                if (b.getBarrierStartCoordinate().getX() > currentCoordinate.getX()
                        && b.getBarrierStartCoordinate().getX() <= futureCoordinate.getX())
                    return true;
                if (b.getBarrierStartCoordinate().getX() <= currentCoordinate.getX()
                        && b.getBarrierStartCoordinate().getX() > futureCoordinate.getX())
                    return true;
            }
        }
        return false;
    }

    private boolean willCrossVerticalBarrier(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        List<Barrier> barriers = getVerticalBarriers();
        for (Barrier b : barriers) {
            if (b.getBarrierStartCoordinate().getX() <= currentCoordinate.getX()
                    && b.getBarrierEndCoordinate().getX() > currentCoordinate.getX()) {
                if (b.getBarrierStartCoordinate().getY() > currentCoordinate.getY()
                        && b.getBarrierStartCoordinate().getY() <= futureCoordinate.getY())
                    return true;
                if (b.getBarrierStartCoordinate().getY() <= currentCoordinate.getY()
                        && b.getBarrierStartCoordinate().getY() > futureCoordinate.getY())
                    return true;
            }
        }
        return false;
    }

    private boolean movesVertically(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        return currentCoordinate.getX() - futureCoordinate.getX() == 0;
    }

    private boolean movesHorizontally(Coordinate currentCoordinate, Coordinate futureCoordinate) {
        return currentCoordinate.getY() - futureCoordinate.getY() == 0;
    }

    private List<Barrier> getVerticalBarriers() {
        return barrierList.stream()
                .filter(barrier -> barrier.getBarrierStartCoordinate().getY() - barrier.getBarrierEndCoordinate().getY() == 0)
                .collect(Collectors.toList());
    }

    private List<Barrier> getHorizontalBarriers() {
        return barrierList.stream()
                .filter(barrier -> barrier.getBarrierStartCoordinate().getX() - barrier.getBarrierEndCoordinate().getX() == 0)
                .collect(Collectors.toList());
    }

}
