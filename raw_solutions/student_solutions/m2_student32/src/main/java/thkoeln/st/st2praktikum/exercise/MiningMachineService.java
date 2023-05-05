package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MiningMachineService {

    private final ArrayList<Field> fields = new ArrayList<>();
    private final ArrayList<MiningMachine> miningMachines = new ArrayList<>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID fieldID = UUID.randomUUID();
        fields.add(new Field(fieldID, height, width));
        return fieldID;
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        for (Field field : fields) {
            if (field.getFieldId().equals(fieldId)) {
                if (barrier.getStart().getX() > field.getWidth() || barrier.getEnd().getX() > field.getWidth() ||
                        barrier.getStart().getY() > field.getHeight() || barrier.getEnd().getY() > field.getHeight()){
                    throw new RuntimeException("Barrier out of Bounds");
                }
                field.addBarrier(barrier);
                return;
            }
        }
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        for (Field sourceField : fields) {
            if (sourceField.getFieldId().equals(sourceFieldId)) {
                if (sourceCoordinate.getY() > sourceField.getHeight() || sourceCoordinate.getX() > sourceField.getWidth()){
                    throw new RuntimeException("Connection out of Bounds");
                }
                for (Field destinationField : fields) {
                    if (destinationField.getFieldId().equals(destinationFieldId)) {
                        if (destinationCoordinate.getY() > destinationField.getHeight() || destinationCoordinate.getX() > destinationField.getWidth()) {
                            throw new RuntimeException("Connection out of Bounds");
                        }
                    }
                }

                return sourceField.addConnection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
            }
        }
        return null;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningMachineId = UUID.randomUUID();
        miningMachines.add(new MiningMachine(miningMachineId, name));
        return miningMachineId;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        if (order.getOrderType() == OrderType.ENTER){
            return entryMiningMachine(miningMachineId, order.getGridId());
        } if (order.getOrderType() == OrderType.TRANSPORT){
            return  transportMiningMachine(miningMachineId, order.getGridId());
        } else {
            return moveMiningMachine(miningMachineId, order.getOrderType(), order.getNumberOfSteps());
        }
    }

    public Boolean moveMiningMachine(UUID miningMachineId, OrderType orderType, Integer steps){
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getMiningMachineId().equals(miningMachineId)) {
                for (Field field : fields) {
                    if (field.getFieldId().equals(miningMachine.getFieldId())) {
                        field.moveMiningMachine(miningMachine, orderType, steps, miningMachines);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Boolean transportMiningMachine(UUID miningMachineId, UUID destinationFieldId) {
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachineId.equals(miningMachine.getMiningMachineId())) {
                for (Field field : fields) {
                    if (field.getFieldId().equals(miningMachine.getFieldId())) {
                        MiningMachine newMiningMachine = field.transportMiningMachine(miningMachine, destinationFieldId, miningMachines);
                        return newMiningMachine != null;
                    }
                }
            }
        }
        return false;
    }

    public Boolean entryMiningMachine(UUID miningMachineId, UUID fieldId) {
        for (Field field : fields) {
            if (field.getFieldId().equals(fieldId)) {
                for (MiningMachine miningMachine : miningMachines) {
                    if (miningMachine.getMiningMachineId().equals(miningMachineId)) {
                        for (MiningMachine otherMiningMachine : miningMachines) {
                            if (fieldId.equals(otherMiningMachine.getFieldId())) {
                                if (otherMiningMachine.getCoordinate().getX() == 0 && otherMiningMachine.getCoordinate().getY() == 0) {
                                    return false;
                                }
                            }
                        }
                        miningMachine.setFieldId(fieldId);
                        miningMachine.setCoordinate(new Coordinate(0,0));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getMiningMachineId().equals(miningMachineId)) {
                return miningMachine.getFieldId();
            }
        }
        return null;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getMiningMachineId().equals(miningMachineId)) {
                return miningMachine.getCoordinate();
            }
        }
        return null;
    }
}
