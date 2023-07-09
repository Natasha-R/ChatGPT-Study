package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService {

    @Autowired
    private MiningMachineHandle handle;
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        return handle.addField(height,width);
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        handle.addObstacle(fieldId,obstacle);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return handle.addTransportTechnology(technology);
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        return handle.addConnection(transportTechnologyId, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        return handle.addMiningMachine(name);
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task){
        return handle.executeCommand(miningMachineId, task);
    }
    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return handle.getMiningMachineFieldId(miningMachineId);
    }
    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return handle.getCoordinates(miningMachineId);
    }
}
