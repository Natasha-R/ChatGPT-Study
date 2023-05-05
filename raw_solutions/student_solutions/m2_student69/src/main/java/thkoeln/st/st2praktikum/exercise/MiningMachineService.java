package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningMachineService {

    Map<UUID, Field> FieldList = new HashMap<>();
    MachineDirections machineDirections = new MachineDirections();


    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID tmp = UUID.randomUUID();
        Field field = new Field(tmp, height, width);
        FieldList.put(tmp, field);

        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = FieldList.get(fieldId);

        /*String[] barrierSplit = barrierString.split("-"); // [0] = (2,3); [1] = (10,3)
        String from = barrierSplit[0]; // (2,3)

        String to = barrierSplit[1]; // (10,3)

        int xFrom = Integer.parseInt(from.split(",")[0].replace("(", ""));
        int xTo = Integer.parseInt(to.split(",")[0].replace("(", ""));

        int yFrom = Integer.parseInt(from.split(",")[1].replace(")", ""));
        int yTo = Integer.parseInt(to.split(",")[1].replace(")", ""));
        */
        if (barrier.getStart().getX().equals(barrier.getEnd().getX())) {
            field.getHORIZONTAL().add(barrier);
            /*for(int i = yFrom; i < yTo; i++) {
                Barrier barrier_a = new Barrier(xFrom, i);
                barrier_a.setCoordinates("(" + xFrom + "," + i + ")");
                field.getHORIZONTAL().add(barrier_a);
            }*/
        } else if (barrier.getStart().getY().equals(barrier.getEnd().getY())) {
            field.getVERTICAL().add(barrier);
            /*for(int i = xFrom; i < xTo; i++) {
                Barrier barrier_a = new Barrier();
                barrier_a.setCoordinates("(" + i + "," + yFrom + ")");
                field.getVERTICAL().add(barrier_a);
            }*/
        }

        FieldList.put(fieldId, field);
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
    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Field sourceField = FieldList.get(sourceFieldId);

        if(destinationFieldId == null || destinationCoordinate == null)
            throw new RuntimeException("Destination cannot be null!");

        if(sourceCoordinate.getX() > sourceField.getWidth() || sourceCoordinate.getX() < 0)
            throw new RuntimeException("Wrong coordinates!");
        if(sourceCoordinate.getY() > sourceField.getHeight() || sourceCoordinate.getY() < 0)
            throw new RuntimeException("Wrong coordinates!");

        Connection con = new Connection(UUID.randomUUID(), sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);

        sourceField.getCONNECTION().add(con);
        FieldList.put(sourceFieldId, sourceField);

        return con.getConnection_ID();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(UUID.randomUUID(), name, new Coordinate(0, 0), null);

        machineDirections.getMachineList().put(machine.getMiningMachine_ID(), machine);


        return machine.getMiningMachine_ID();
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
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        MiningMachine machine = machineDirections.getMachineList().get(miningMachineId);
        Field field = machine.getField();

        switch (task.getTaskType()) {
            case NORTH:
                machineDirections.north(field.getVERTICAL(), task.getNumberOfSteps(), machine);
                break;
            case SOUTH:
                machineDirections.south(field.getVERTICAL(), task.getNumberOfSteps(), machine);
                break;
            case EAST:
                machineDirections.east(field.getHORIZONTAL(), task.getNumberOfSteps(), machine);
                break;
            case WEST:
                machineDirections.west(field.getHORIZONTAL(), task.getNumberOfSteps(), machine);
                break;
            case TRANSPORT:
                return machineDirections.transfer(machine, task.getGridId(), field, FieldList);
            case ENTER:
                return machineDirections.entrance(machine, task.getGridId(), FieldList);
        }
        System.out.println("[" + task.getTaskType() + "," + task.getNumberOfSteps() + "]");
        System.out.println("(" + machine.getCoordinate().getX() + "," + machine.getCoordinate().getY() + ") ");
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return machineDirections.getMachineList().get(miningMachineId).getField().getId();
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId) {
        MiningMachine machine = machineDirections.getMachineList().get(miningMachineId);
        return machine.getCoordinate();
    }
}
