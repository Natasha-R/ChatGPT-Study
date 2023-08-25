package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


public class MiningMachineService {

private final CommandMiningMachine commandMiningMachine;
private final ArrayList<Connections> connections;

public MiningMachineService(){
    commandMiningMachine=new CommandMiningMachine();
    connections=new ArrayList<>();
}

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField=new Field(height,width);
        commandMiningMachine.addField(newField);
        return newField.getUUID();
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        if(commandMiningMachine.fieldExists(fieldId)){
            commandMiningMachine.getField(fieldId).addObstacles(obstacle);
        }
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Vector2D sourceVector2D, UUID destinationFieldId, Vector2D destinationVector2D) {
        if(commandMiningMachine.fieldExists(sourceFieldId) && commandMiningMachine.fieldExists(destinationFieldId)){
            Connections connection=new Connections(sourceFieldId, sourceVector2D,
                    destinationFieldId, destinationVector2D);
            connections.add(connection);
            return connection.getUUID();
        }
        return null;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine=new MiningMachine(name);
        commandMiningMachine.addMiningMachine(miningMachine);
        return miningMachine.getUUID();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        if(commandMiningMachine.getMiningMachine(miningMachineId)!=null){
            if(isCompassDirection(command.getCommandType())){
                commandMiningMachine.go(miningMachineId, command.getNumberOfSteps(),command.getCommandType());
                return true;
            }
            else if(command.getCommandType().equals(CommandType.TRANSPORT)){
                return transportMiningMaschine(miningMachineId,command.getGridId());
            }
            else if(command.getCommandType().equals(CommandType.ENTER)){
                return spwanMiningMaschine(miningMachineId,command.getGridId());
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
        if(commandMiningMachine.getMiningMachine(miningMachineId).getField()!=null) {
            return commandMiningMachine.getMiningMachine(miningMachineId).getField().getUUID();
        }
        return null;
    }

    /**
     * This method returns the vector2Ds a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the vector2Ds of the mining machine
     */
    public Vector2D getVector2D(UUID miningMachineId){
        if(commandMiningMachine.getMiningMachine(miningMachineId) != null){
            return commandMiningMachine.getMiningMachine(miningMachineId).getCurrentPlace();
        }
        throw new RuntimeException();
    }

    private boolean transportMiningMaschine(UUID pMiningMachineId, UUID pFieldID){
        if(commandMiningMachine.fieldExists(pFieldID)){
            Field aktuellesfeldDerMiningMaschine=commandMiningMachine.getField(getMiningMachineFieldId(pMiningMachineId));
            if(isAMiningMaschineStandingOnAConnection(pMiningMachineId,aktuellesfeldDerMiningMaschine.getUUID())!=null){
                Connections zielConnection=isAMiningMaschineStandingOnAConnection(pMiningMachineId,aktuellesfeldDerMiningMaschine.getUUID());
                if(!commandMiningMachine.isFree(zielConnection.getDestinationCoordinate(), pFieldID))return false;
                commandMiningMachine.addMiningMachinetoField(commandMiningMachine.getMiningMachine(pMiningMachineId),zielConnection.getDestinationFieldId(),zielConnection.getDestinationCoordinate());
                commandMiningMachine.getMiningMachine(pMiningMachineId).changeField(commandMiningMachine.getField(zielConnection.getDestinationFieldId()));
                return true;
            }
        }
        return false;
    }

    public Connections isAMiningMaschineStandingOnAConnection(UUID pUUIDVonMiningMaschine, UUID pFieldID){
        if(commandMiningMachine.getMiningMachine(pUUIDVonMiningMaschine).getCurrentPlace()!=null) {
            Vector2D koordinateFuerDieMiningMaschine = commandMiningMachine.getMiningMachine(pUUIDVonMiningMaschine).getCurrentPlace();
            for (Connections connection : connections) {
                if (connection.getSourceFieldId().equals(pFieldID)) {
                    if (connection.getSourceCoordinate().getX().equals(koordinateFuerDieMiningMaschine.getX()))
                        return connection;
                }
            }
        }
        return null;
    }

    private boolean spwanMiningMaschine(UUID pMiningMaschineID,UUID pFieldID){
        if(commandMiningMachine.fieldExists(pFieldID)){
            if(!Objects.requireNonNull(commandMiningMachine.getMiningMachine(pMiningMaschineID)).hasField()){
                if(commandMiningMachine.getField(pFieldID)!=null){
                    if(commandMiningMachine.isFree(new Vector2D(0,0), pFieldID)){
                        commandMiningMachine.getMiningMachine(pMiningMaschineID).changeField(commandMiningMachine.getField(pFieldID));
                        return commandMiningMachine.addMiningMachinetoField(commandMiningMachine.getMiningMachine(pMiningMaschineID),pFieldID,new Vector2D(0,0));
                    }
                }
            }
        }
        return false;
    }

    private boolean isCompassDirection(CommandType pCommandType){
        return pCommandType.equals(CommandType.NORTH) || pCommandType.equals(CommandType.SOUTH)
                || pCommandType.equals(CommandType.EAST) || pCommandType.equals(CommandType.WEST);
    }

}
