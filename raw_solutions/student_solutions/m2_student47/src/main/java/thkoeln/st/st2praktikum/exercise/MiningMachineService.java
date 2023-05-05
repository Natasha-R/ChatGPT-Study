package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MiningMachineService {

    private final ArrayList<Field> fields = new ArrayList<>();
    private final ArrayList<MiningMachine> machines = new ArrayList<>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width,height);
        fields.add(field);
        return field.getFieldID();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, Barrier barrierString) {
        for (Field value : fields) {
            if (value.getFieldID() == fieldId) {
                if(barrierString.getStart().getX() > value.getWidth()
                        || barrierString.getEnd().getX() > value.getWidth()
                        || barrierString.getStart().getY() > value.getHeight()
                        || barrierString.getEnd().getY() > value.getHeight())throw new RuntimeException("barrier out of bounds");
                value.getBarriers().add(barrierString);
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
    public UUID addConnection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
        Connection connection = new Connection(sourceFieldId,sourceCoordinate,destinationFieldId,destinationCoordinate);
        for (Field value : fields) {
            if(value.getFieldID()==destinationFieldId){
                if(value.getWidth() < destinationCoordinate.getX() || value.getHeight() < destinationCoordinate.getY()) throw new RuntimeException("destination Out of Bounds");
            }
            if (value.getFieldID() == sourceFieldId) {
                if(sourceCoordinate.getX() > value.getWidth() || sourceCoordinate.getY() > value.getHeight())throw new RuntimeException("source out of bounds");
                value.getConnections().add(connection);
            }
        }
        return connection.getSourceField();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        machines.add(machine);
        return machine.getMachineID();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        UUID fieldID ;
        for (MiningMachine miningMachine : machines) {
            if (miningMachine.getMachineID() == miningMachineId) {
                fieldID = miningMachine.getFieldID();
                return fieldID;
            }
        }
        return null;
    }

    public Point getPoint(UUID miningMachineId){
        for (MiningMachine miningMachine : machines) {
            if (miningMachine.getMachineID() == miningMachineId) {
                return miningMachine.getPoint();
            }
        }
        return null;
    }

    public Boolean executeCommand(UUID miningMachineId, Command commandString) {
        return new MachineCommander(fields, machines).executeCommand(miningMachineId,commandString);
    }
}
