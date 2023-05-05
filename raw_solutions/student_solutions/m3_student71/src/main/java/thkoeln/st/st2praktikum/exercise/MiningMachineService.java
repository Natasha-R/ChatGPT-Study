package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.HashMap;
import java.util.UUID;


@Service
public class MiningMachineService {

    World world = new World();
    HashMap<UUID, TransportCategory> transportCategories = new HashMap<>();

    @Autowired
    private MiningMachineRepository miningMachineRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private TileRepository tileRepository;

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var field = new Field(height,width);
        world.addField(field);

        var Tiles = field.getBoard();

        for (int i = 0; i < height*width; i++) {
            tileRepository.save(Tiles.get(i));
        }

        fieldRepository.save(field);

        return field.getUuid();
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        try {
            world.getField(fieldId).addObstacle(obstacle);

        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        var TransportCategory = new TransportCategory(category);
        transportCategories.put(TransportCategory.getUuid(),TransportCategory);
        return TransportCategory.getUuid();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        var sourceField = world.getField(sourceFieldId);
        var connection = new Connection(sourceField,world.getField(destinationFieldId), sourceCoordinate, destinationCoordinate);

        var transportCategory = transportCategories.get(transportCategoryId);
        transportCategory.getConnections().add(connection);

        sourceField.setConnection(connection);

        return connection.getUuid();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var miningMachine = new MiningMachine(name);
        world.putMiningMachine(miningMachine.getUuid(),miningMachine);
        //miningMachineRepository.save(miningMachine);
        return miningMachine.getUuid();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        try {

            MiningMachine tempMiningMachine = world.getMiningMachine(miningMachineId);

            if(command.getCommandType() == CommandType.ENTER)
            {
                world.executeCommand(miningMachineId, command);
            }
            else
            {
                tempMiningMachine.executeCommand(command);
            }

            if(miningMachineRepository.existsById(tempMiningMachine.getUuid()))
                miningMachineRepository.deleteById(tempMiningMachine.getUuid());

            miningMachineRepository.save(tempMiningMachine);

        } catch (InvalidAttributeValueException | SpawnMiningMachineException | TeleportMiningMachineException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return world.getMiningMachine(miningMachineId).getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        return world.getMiningMachine(miningMachineId).getCoordinate();
    }
}
