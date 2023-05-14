package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import thkoeln.st.st2praktikum.exercise.entities.Field;
import thkoeln.st.st2praktikum.exercise.entities.FieldConnection;
import thkoeln.st.st2praktikum.exercise.entities.MiningMachine;
import thkoeln.st.st2praktikum.exercise.valueObjects.Barrier;
import thkoeln.st.st2praktikum.exercise.valueObjects.Coordinate;

import java.util.UUID;

public class MiningMachineService {

    private final MiningMachineController controller = new MiningMachineController();

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        controller.addField(field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId       the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {
        Barrier barrier = new Barrier(barrierString);
        controller.addBarrier(fieldId, barrier);
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
        FieldConnection fieldConnection = new FieldConnection(
                sourceFieldId,
                destinationFieldId,
                Coordinate.parseCoordinate(sourceCoordinate),
                Coordinate.parseCoordinate(destinationCoordinate)
        );
        controller.addFieldConnection(fieldConnection);
        return fieldConnection.getId();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        controller.addMiningMachine(miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param commandString   the given command, encoded as a String:
     *                        "[direction, steps]" for movement, e.g. "[we,2]"
     *                        "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another field
     *                        "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        return controller.executeCommand(miningMachineId, commandString);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return controller.getMiningMachineFieldId(miningMachineId);
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId) {
        Coordinate coordinates = controller.getCoordinates(miningMachineId);
        if (coordinates.equals(new Coordinate(-1, -1))) {
            throw new ResourceNotFoundException("There is no Mining Machine with this ID on a Field");
        }
        return String.format("(%d,%d)", coordinates.getX(), coordinates.getY());
    }

}
