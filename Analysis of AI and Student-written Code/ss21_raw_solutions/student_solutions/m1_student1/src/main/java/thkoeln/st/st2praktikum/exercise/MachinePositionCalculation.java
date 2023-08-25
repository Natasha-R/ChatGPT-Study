package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.UUID;

public class MachinePositionCalculation {

    private int currentFieldHeight;
    private int currentFieldWidth;
    private int fieldRange;
    private int machinePositionOnAxis;
    private int barrierStart;
    private int barrierEnd;
    private int barrierPosition;
    private boolean verticalDirection;
    private boolean positiveDirection;
    private boolean commandSuccessfull = true;

    private final MiningMachineManager miningMachineManager;

    protected MachinePositionCalculation(MiningMachineManager miningMachineManager) {
        this.miningMachineManager = miningMachineManager;
    }

    public boolean getCommandSuccesfull() {
        return this.commandSuccessfull;
    }

    protected void spawn(UUID currentFieldID, MiningMachine miningMachine) {

        System.out.println("Mining machine " + miningMachine.getName() + " sagt hallo!");

        for (MiningMachine miningMachines : miningMachineManager.getMiningMachines()) {
            if (miningMachines.getUUID() != miningMachine.getUUID() && miningMachines.getCurrentFieldID().equals(currentFieldID)) {
                if (miningMachines.getPosition().getX() == 0 && miningMachines.getPosition().getY() == 0)
                    commandSuccessfull = false;
                break;
            }
        }
        if (commandSuccessfull) {
            miningMachine.setCurrentFieldID(currentFieldID);
        }
        setFieldSpecs(currentFieldID);

    }

    protected void transport(MiningMachine miningMachine) {
        commandSuccessfull = false;
        for (Deck deck : miningMachineManager.miningMachineService.deck.getDecks()) {
            if (commandSuccessfull)
                break;
            if (deck.getFieldID().equals(miningMachine.getCurrentFieldID())) {
                for (Connection connection : deck.getConnections()) {
                    if (miningMachine.getCurrentFieldID().equals(connection.getStartID())) {
                        if (miningMachine.getPosition().getX() == connection.getStartLocation().getX() && miningMachine.getPosition().getY() == connection.getStartLocation().getY()) {
                            commandSuccessfull = true;
                            UUID currentFieldID = connection.getEndID();
                            miningMachine.setCurrentFieldID(currentFieldID);
                            Point point = new Point(connection.getEndLocation().getX(), connection.getEndLocation().getY());
                            miningMachine.setPosition(point);
                            setFieldSpecs(currentFieldID);
                            System.out.println("Mining machine " + miningMachine.getName() + " hat das neue Feld erreicht.");
                            break;
                        }
                    }

                }
            }
        }
    }

    protected void north(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        setCoordinate(distanceDirectionFieldID, miningMachine);
    }

    protected void south(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        setCoordinate(distanceDirectionFieldID, miningMachine);

    }

    protected void east(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        setCoordinate(distanceDirectionFieldID, miningMachine);
    }

    protected void west(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        setCoordinate(distanceDirectionFieldID, miningMachine);
    }

    private void setCoordinate(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        int firstBarrier = firstBarrier(miningMachine, distanceDirectionFieldID.getCommandType());
        int newPos = getNewPos(distanceDirectionFieldID, miningMachine);
        boolean barrierInWay = firstBarrier != -1;

        setMovementDirections(distanceDirectionFieldID.getCommandType());
        setFieldRange();

        if (positiveDirection) {
            if (newPos > fieldRange)
                newPos = fieldRange - 1;
            if (newPos >= firstBarrier && barrierInWay)
                newPos = firstBarrier - 1;
        }

        if (!positiveDirection) {
            if (newPos < 0)
                newPos = 0;
            if (newPos < firstBarrier)
                newPos = firstBarrier;
        }

        if (verticalDirection)
            miningMachine.setPosition(new Point(newPos, miningMachine.getPosition().getY()));
        else
            miningMachine.setPosition(new Point(miningMachine.getPosition().getX(), newPos));
    }

    private void setFieldRange() {
        if (verticalDirection)
            fieldRange = currentFieldWidth;
        else
            fieldRange = currentFieldHeight;
    }

    private void setFieldSpecs(UUID currentFieldID) {
        for (int i = 0; i < miningMachineManager.miningMachineService.deck.getDecks().size(); i++) {
            if (miningMachineManager.miningMachineService.deck.getDecks().get(i).getFieldID().equals(currentFieldID)) {
                currentFieldHeight = miningMachineManager.miningMachineService.deck.getDecks().get(i).getFieldHeight();
                currentFieldWidth = miningMachineManager.miningMachineService.deck.getDecks().get(i).getFieldWidth();
            }
        }
    }

    private void setMovementDirections(CommandType commandType) {
        this.verticalDirection = commandType.equals(CommandType.EAST) || commandType.equals(CommandType.WEST);
        this.positiveDirection = commandType.equals(CommandType.EAST) || commandType.equals(CommandType.NORTH);
    }

    private int firstBarrier(MiningMachine miningMachine, CommandType commandType) {

        int firstBarrier = -1;
        setMachinePositionOnAxis(verticalDirection, miningMachine);

        for (Deck deck : miningMachineManager.miningMachineService.deck.getDecks()) {
            if (deck.getFieldID().equals(miningMachine.getCurrentFieldID())) {
                for (Barrier barrier : deck.getBarriers()) {
                    setBarrierSpecs(barrier);

                    firstBarrier = checkBarrierInWay(barrier.getAxis(), commandType, miningMachine, firstBarrier);
                }
            }
        }
        return firstBarrier;
    }

    private int getNewPos(DistanceDirectionFieldID distanceDirectionFieldID, MiningMachine miningMachine) {
        switch (distanceDirectionFieldID.getCommandType()) {
            case NORTH:
                return miningMachine.getPosition().getY() + distanceDirectionFieldID.getDistance();
            case EAST:
                return miningMachine.getPosition().getX() + distanceDirectionFieldID.getDistance();
            case SOUTH:
                return miningMachine.getPosition().getY() - distanceDirectionFieldID.getDistance();
            case WEST:
                return miningMachine.getPosition().getX() - distanceDirectionFieldID.getDistance();
            default:
                throw new InvalidParameterException("Invalid direction");
        }
    }

    private void setBarrierSpecs(Barrier barrier) {
        if (verticalDirection) {
            this.barrierStart = barrier.getStartPoint().getX();
            this.barrierEnd = barrier.getEndPoint().getX();
            this.barrierPosition = barrier.getEndPoint().getY();
        } else {
            this.barrierStart = barrier.getStartPoint().getY();
            this.barrierEnd = barrier.getEndPoint().getY();
            this.barrierPosition = barrier.getEndPoint().getX();
        }
    }

    private void setMachinePositionOnAxis(boolean vertical, MiningMachine miningMachine) {
        if (vertical) {
            this.machinePositionOnAxis = miningMachine.getPosition().getX();
        } else {
            this.machinePositionOnAxis = miningMachine.getPosition().getY();
        }
    }

    private int checkBarrierInWay(Axis axis, CommandType commandType, MiningMachine miningMachine, int firstBarrier) {

        if (machinePositionOnAxis >= barrierStart && machinePositionOnAxis < barrierEnd && axis.equals(Axis.HORIZONTAL)) {
            if (commandType.equals(CommandType.NORTH) && barrierPosition >= miningMachine.getPosition().getY()) {
                return barrierPosition;
            }

            if (commandType.equals(CommandType.SOUTH) && barrierPosition <= miningMachine.getPosition().getY()) {
                return barrierPosition;
            }
        }
        if (machinePositionOnAxis >= barrierStart && machinePositionOnAxis <= barrierEnd && axis.equals(Axis.VERTICAL)) {
            if (commandType.equals(CommandType.EAST) && barrierPosition >= miningMachine.getPosition().getX()) {
                return barrierPosition;
            }

            if (commandType.equals(CommandType.WEST) && barrierPosition <= miningMachine.getPosition().getX()) {
                return barrierPosition;
            }
        }
        return firstBarrier;
    }
}