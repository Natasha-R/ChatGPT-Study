package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.transportSystem.Connection;
import thkoeln.st.st2praktikum.exercise.transportSystem.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportSystem.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportSystem.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.FieldRepository;
import thkoeln.st.st2praktikum.exercise.moveable.MiningMachineMovement;

import java.util.UUID;


@Service
public class MiningMachineService {
    @Autowired
    MiningMachineMovement miningMachineMovement = new MiningMachineMovement();

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    MiningMachineRepository miningMachineRepository;

    @Autowired
    TransportSystemRepository transportSystemRepository;

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField = new Field(height, width);
        fieldRepository.save(newField);
        return newField.getUuid();
    }

    /**
     * This method adds a obstacle to a given field.
     *
     * @param fieldId  the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        fieldRepository.findById(fieldId).get().ceckPointToBeWithinBoarders(obstacle.getStart());
        fieldRepository.findById(fieldId).get().ceckPointToBeWithinBoarders(obstacle.getEnd());
        fieldRepository.findById(fieldId).get().addObstacle(obstacle);
    }

    /**
     * This method adds a transport system
     *
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem newTransportSystem = new TransportSystem(system);
        transportSystemRepository.save(newTransportSystem);
        return newTransportSystem.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     *
     * @param transportSystemId  the transport system which is used by the connection
     * @param sourceFieldId      the ID of the field where the entry point of the connection is located
     * @param sourcePoint        the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint   the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        TransportSystem temp = transportSystemRepository.findById(transportSystemId).get();
        Connection newConnection = new Connection(temp, sourceFieldId, sourcePoint, destinationFieldId, destinationPoint);
        fieldRepository.findById(sourceFieldId).get().ceckPointToBeWithinBoarders(sourcePoint);
        fieldRepository.findById(sourceFieldId).get().ceckPointToBeWithinBoarders(destinationPoint);
        connectionRepository.save(newConnection);
        //transportSystemRepository.findById(transportSystemId).get().addConnection(newConnection);
        return newConnection.getUuid();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine newMiningMachine = new MiningMachine(name);
        miningMachineRepository.save(newMiningMachine);
        return newMiningMachine.getUuid();
    }

    /**
     * This method lets the mining machine execute a task.
     *
     * @param miningMachineId the ID of the mining machine
     * @param task            the given task
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on squares with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a obstacle or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        if (miningMachineRepository.findById(miningMachineId).isPresent()) {
            return miningMachineMovement.move(task, miningMachineId);
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return miningMachineRepository.findById(miningMachineId).get().getUuid();
    }

    /**
     * This method returns the point a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getPoint(UUID miningMachineId) {
        return miningMachineRepository.findById(miningMachineId).get().getPoint();
    }
}
