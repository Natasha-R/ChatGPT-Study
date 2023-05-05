package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.field.FieldConnection;
import thkoeln.st.st2praktikum.exercise.util.GameManager;

import java.util.UUID;

public class MiningMachineService {
    private final GameManager gameManager;

    public MiningMachineService() {
        this.gameManager = new GameManager();
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID uuid = UUID.randomUUID();
        Field field = new Field(uuid, height, width);
        gameManager.getFields().put(uuid, field);
        return uuid;
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = gameManager.getFields().get(fieldId);

        int fromX = wall.getStart().getX();
        int fromY = wall.getStart().getY();
        int toX = wall.getEnd().getX();
        int toY = wall.getEnd().getY();

        if (fromX == toX) {
                field = gameManager.getWallManager().registerWall(field, OrderType.NORTH, fromY, toY, fromX);
        }else {
                field = gameManager.getWallManager().registerWall(field, OrderType.EAST, fromX, toX, fromY);
        }
        gameManager.getFields().replace(fieldId, field);
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
        Field sourceField = gameManager.getFields().get(sourceFieldId);
        Field destField = gameManager.getFields().get(destinationFieldId);
        FieldConnection fieldConnection = new FieldConnection(sourceField, sourceCoordinate, destField, destinationCoordinate);
        UUID uuid = UUID.randomUUID();
        sourceField.getFieldConnections().put(uuid, fieldConnection);
        gameManager.getFields().replace(sourceFieldId, sourceField);
        return uuid;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        UUID uuid = UUID.randomUUID();
        gameManager.getMachines().put(uuid, machine);
        return uuid;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        System.out.println("[" + order.getOrderType().name() + "," + order.getNumberOfSteps() + "]");
        MiningMachine machine = gameManager.getMachines().get(miningMachineId);
        Field field = gameManager.getFields().get(machine.getFieldId());
        Pair<Boolean, MiningMachine> pair = gameManager.onMovement(machine, field, order);
        if(pair.getFirst())
            gameManager.getMachines().replace(miningMachineId, pair.getSecond());
        System.out.println("(" + pair.getSecond().getCoordinate().getX() + "," + pair.getSecond().getCoordinate().getY() + ")");
        return pair.getFirst();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return gameManager.getMachines().get(miningMachineId).getFieldId();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId){
        return gameManager.getMachines().get(miningMachineId).getCoordinate();
    }
}
