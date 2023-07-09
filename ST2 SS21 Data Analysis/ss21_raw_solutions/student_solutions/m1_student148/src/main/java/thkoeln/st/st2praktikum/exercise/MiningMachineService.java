package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {
    public  HashMap<UUID, MiningMachine> miningMachines = new HashMap<>();
    public  HashMap<UUID, Field> fields = new HashMap<>();
    public  HashMap<UUID, Connection> connections = new HashMap<>();

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height, width);
        fields.put(field.getId(), field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId       the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {
        Barrier bar = new Barrier();
        int[] barrier = bar.deString(barrierString);
        Field field = fields.get(fieldId);
        field.getBarriers().add(new Barrier(barrier[0], barrier[1], barrier[2], barrier[3]));
        fields.put(field.getId(), field);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        Connection con = new Connection();
        int[] SourceConnection = con.deString(sourceCoordinate);
        int[] destinationConnection = con.deString(destinationCoordinate);
        Point srcCoordinate = new Point(SourceConnection[0], SourceConnection[1]);
        Point dstCoordinate = new Point(destinationConnection[0], destinationConnection[1]);
        Connection connection = new Connection(sourceFieldId, srcCoordinate, destinationFieldId, dstCoordinate);
        connections.put(connection.getId(), connection);
        return connection.getId();

    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachines.put(miningMachine.getId(), miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param commandString   the given command, encoded as a String:
     *                        "[direction, steps]" for movement, e.g. "[we,2]"
     *                        "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another field
     *                        "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        Movement move = new Movement(this.miningMachines, this.fields, this.connections);
        return move.moveTo(miningMachineId, commandString);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        if (machine.getState() != MachineDeploymentState.HIBERNATING) {
            return machine.getCurrentField().getId();
        }
        throw new IllegalArgumentException("Mining Machine" + machine.getId() + "has not been deployed on any field yet");
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId) {
        return miningMachines.get(miningMachineId).getPositionString();
    }
}