package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class MiningMachineService {

    private ArrayList<Field> fields = new ArrayList<>();
    private ArrayList<MiningMachine> miningMachines = new ArrayList<>();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID fieldID = UUID.randomUUID();
        fields.add(new Field(fieldID,height,width));
        return fieldID;
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {
        for (Field field : fields){
            if (field.getFieldId().equals(fieldId)){
                field.addBarrier(barrierString);
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
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        for (Field field : fields){
            if (field.getFieldId().equals(sourceFieldId)){
                return field.addConnection(sourceFieldId,sourceCoordinate, destinationFieldId, destinationCoordinate);
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
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {

        //Handle commandString
        String[] commands = commandString.substring(1, commandString.length()-1).split(",");
        String directionString = commands[0];
        Direction direction;
        switch (directionString){
            case "no": direction = Direction.NORTH;break;
            case "ea": direction = Direction.EAST;break;
            case "so": direction = Direction.SOUTH;break;
            case "we": direction = Direction.WEST;break;
            case "en": return entryMiningMachine(miningMachineId, commands[1]);
            case "tr": return transportMiningMachine(miningMachineId, commands[1]);
            default: return false;
        }



        for (MiningMachine miningMachine : miningMachines){
            if (miningMachine.getMiningMachineId().equals(miningMachineId)){
                for (Field field : fields){
                    ArrayList<MiningMachine> otherMiningMachines = new ArrayList<>();
                    for (MiningMachine otherMiningMachine: miningMachines){
                        if (miningMachine.getFieldId().equals(otherMiningMachine.getFieldId())){
                            otherMiningMachines.add(otherMiningMachine);
                        }
                    }
                    if (field.getFieldId().equals(miningMachine.getFieldId())){
                        int steps = Integer.parseInt(commands[1]);
                        miningMachine = field.moveMiningMachine(miningMachine, direction, steps, otherMiningMachines);
                    }
                }
            }
        }

        return true;
    }
    public Boolean transportMiningMachine(UUID miningMachineId, String destinationFieldIdString){
        UUID destinationFieldId = UUID.fromString(destinationFieldIdString);
        for (MiningMachine miningMachine : miningMachines){
            if (miningMachineId.equals(miningMachine.getMiningMachineId())){
                for (Field field : fields){
                    if (field.getFieldId().equals(miningMachine.getFieldId())){
                        MiningMachine newMiningMachine = field.transportMiningMachine(miningMachine, destinationFieldId, miningMachines);
                        if (newMiningMachine == null){
                            return false;
                        } else {
                            miningMachine.setFieldId(destinationFieldId);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Boolean entryMiningMachine(UUID miningMachineId, String fieldIdString){
        UUID fieldId = UUID.fromString(fieldIdString);
        for (Field field : fields){
            if (field.getFieldId().equals(fieldId)){
                for (MiningMachine miningMachine : miningMachines){
                    if (miningMachine.getMiningMachineId().equals(miningMachineId)){
                        for (MiningMachine otherMiningMachine : miningMachines){
                            if (fieldId.equals(otherMiningMachine.getFieldId())){
                                if (otherMiningMachine.getXPos() == 0 && otherMiningMachine.getYPos() == 0){
                                    return false;
                                }
                            }
                        }
                        miningMachine.setFieldId(fieldId);
                        miningMachine.setXPos(0);
                        miningMachine.setYPos(0);
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
        for (MiningMachine miningMachine : miningMachines){
            if (miningMachine.getMiningMachineId().equals(miningMachineId)){
                return miningMachine.getFieldId();
            }
        }
        return null;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachines){
            if (miningMachine.getMiningMachineId().equals(miningMachineId)){
                return miningMachine.getCoordinates();
            }
        }
        return null;
    }
}
