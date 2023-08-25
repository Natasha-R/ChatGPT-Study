package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Field {
    private final UUID fieldId;
    private final Integer height;
    private final Integer width;
    private final ArrayList<Barrier> barriers = new ArrayList<>();
    private final ArrayList<Connection> connections = new ArrayList<>();

    public Field(UUID fieldID, Integer height, Integer width) {
        this.fieldId = fieldID;
        this.height = height;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public MiningMachine transportMiningMachine(MiningMachine miningMachine, UUID destinationFieldId, ArrayList<MiningMachine> otherMiningMachines) {
        for (Connection connection : connections) {
            if (connection.getDestinationFieldId().equals(destinationFieldId)) {
                if (miningMachine.getCoordinate().equals(connection.getSourceCoordinate())) {
                    //er steht auf tp feld
                    for (MiningMachine otherMiningMachine : otherMiningMachines) {
                        if (destinationFieldId.equals(otherMiningMachine.getFieldId())) {
                            if (otherMiningMachine.getCoordinate().getX() != null) {
                                if (connection.getDestinationCoordinate().equals(otherMiningMachine.getCoordinate())) {
                                    return null;
                                }
                            }
                        }
                    }
                    miningMachine.setFieldId(destinationFieldId);
                    miningMachine.getCoordinate().setX(connection.getDestinationCoordinate().getX());
                    System.out.println(connection.getDestinationCoordinate().getX());
                    miningMachine.getCoordinate().setY(connection.getDestinationCoordinate().getY());
                    System.out.println(connection.getDestinationCoordinate().getY());
                    return miningMachine;
                }
            }
        }
        return null;
    }

    public MiningMachine moveMiningMachine(MiningMachine miningMachine, OrderType direction, int steps, ArrayList<MiningMachine> miningMachines) {
        ArrayList<MiningMachine> otherMiningMachines = new ArrayList<>();
        for (MiningMachine otherMiningMachine : miningMachines){
            if (fieldId == otherMiningMachine.getFieldId()){
                otherMiningMachines.add(otherMiningMachine);
            }
        }

        System.out.println("start=" + miningMachine.getCoordinate().getX() + ":" + miningMachine.getCoordinate().getY() + "|" + direction + "-->" + steps);
        for (int i = 0; i < steps; i++) {

            if (direction == OrderType.NORTH) {
                if (checkNorth(miningMachine.getCoordinate().getX(), miningMachine.getCoordinate().getY(), otherMiningMachines)) {
                    miningMachine.moveNorth();
                } else return miningMachine;
            }

            if (direction == OrderType.EAST) {
                if (checkEast(miningMachine.getCoordinate().getX(), miningMachine.getCoordinate().getY(), otherMiningMachines)) {
                    miningMachine.moveEast();
                } else return miningMachine;
            }

            if (direction == OrderType.SOUTH) {
                if (checkSouth(miningMachine.getCoordinate().getX(), miningMachine.getCoordinate().getY(), otherMiningMachines)) {
                    miningMachine.moveSouth();
                } else return miningMachine;
            }

            if (direction == OrderType.WEST) {
                if (checkWest(miningMachine.getCoordinate().getX(), miningMachine.getCoordinate().getY(), otherMiningMachines)) {
                    miningMachine.moveWest();
                } else return miningMachine;
            }
            System.out.println(miningMachine.getCoordinate().getX() + ":" + miningMachine.getCoordinate().getY());
        }
        return miningMachine;
    }

    private boolean checkNorth(int xPos, int yPos, ArrayList<MiningMachine> miningMachines) {
        if (yPos == height) {
            return false;
        }
        for (Barrier barrier : barriers) {
            if (barrier.isHorizontal()) {
                if (yPos + 1 == barrier.getStart().getY()) {
                    if (barrier.getStart().getX() <= xPos && barrier.getEnd().getX() >= xPos + 1) {
                        return false;
                    }
                }
            }
        }

        for (MiningMachine miningMachine : miningMachines) {
            if (yPos + 1 == miningMachine.getCoordinate().getY() && xPos == miningMachine.getCoordinate().getX()) {
                return false;
            }
        }

        return true;
    }

    private boolean checkSouth(int xPos, int yPos, ArrayList<MiningMachine> miningMachines) {
        if (yPos == 0) {
            return false;
        }
        for (Barrier barrier : barriers) {
            if (barrier.isHorizontal()) {
                if (yPos == barrier.getStart().getY()) {
                    if (barrier.getStart().getX() <= xPos && barrier.getEnd().getX() >= xPos + 1) {
                        return false;
                    }
                }
            }
        }

        for (MiningMachine miningMachine : miningMachines) {
            if (yPos - 1 == miningMachine.getCoordinate().getY() && xPos == miningMachine.getCoordinate().getX()) {
                return false;
            }
        }

        return true;
    }

    private boolean checkEast(int xPos, int yPos, ArrayList<MiningMachine> miningMachines) {
        if (xPos == width) {
            return false;
        }
        for (Barrier barrier : barriers) {
            if (barrier.isVertical()) {
                if (xPos + 1 == barrier.getStart().getX()) {
                    if (barrier.getStart().getY() <= yPos && barrier.getEnd().getY() >= yPos + 1) {
                        return false;
                    }
                }
            }
        }

        for (MiningMachine miningMachine : miningMachines) {
            if (xPos + 1 == miningMachine.getCoordinate().getX() && yPos == miningMachine.getCoordinate().getY()) {
                return false;
            }
        }

        return true;
    }

    private boolean checkWest(int xPos, int yPos, ArrayList<MiningMachine> miningMachines) {
        if (xPos == 0) {
            return false;
        }
        for (Barrier barrier : barriers) {
            if (barrier.isVertical()) {
                if (xPos == barrier.getStart().getX()) {
                    if (barrier.getStart().getY() <= yPos && barrier.getEnd().getY() >= yPos + 1) {
                        return false;
                    }
                }
            }
        }

        for (MiningMachine miningMachine : miningMachines) {
            if (xPos - 1 == miningMachine.getCoordinate().getX() && yPos == miningMachine.getCoordinate().getY()) {
                return false;
            }
        }

        return true;
    }

    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        UUID connectionId = UUID.randomUUID();
        connections.add(new Connection(connectionId, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate));
        return connectionId;
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public UUID getFieldId() {
        return fieldId;
    }
}
