package thkoeln.st.st2praktikum.exercise.world2;

import thkoeln.st.st2praktikum.exercise.world2.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDecisionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDirectionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.MiningmaschineNotSpawnedException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.field.Field;
import thkoeln.st.st2praktikum.exercise.world2.miningMaschine.Miningmaschine;
import thkoeln.st.st2praktikum.exercise.world2.services.CommandService;
import thkoeln.st.st2praktikum.exercise.world2.services.CoordinateService;
import thkoeln.st.st2praktikum.exercise.world2.types.Command;

import java.util.HashMap;
import java.util.UUID;

public class World implements Cloud {

    private final HashMap<UUID, Field> fieldHashMap;
    private final HashMap<UUID, Miningmaschine> miningmaschineHashMap;

    public World(){
        this.fieldHashMap = new HashMap<>();
        this.miningmaschineHashMap = new HashMap<>();
    }

    public UUID addField(Integer height, Integer width) {
        final Field field = new Field(height,width);
        this.fieldHashMap.put(field.getId(), field);
        return field.getId();
    }

    public void addBarrier(UUID fieldId, String barrierString) {
        final Barrier barrier = new Barrier(fieldId, barrierString);
        this.fieldHashMap.get(fieldId).getBarrierList().add(barrier);
    }

    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate){
        try {
            final Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
            this.fieldHashMap.get(sourceFieldId).getSquareFromCoordinate(CoordinateService.stringToCoordinate(sourceCoordinate)).setConnection(connection);
            return connection.getId();
        }catch (NoSquareFoundException noSquareFoundException){
            System.out.println(
                    "Exception beim zugriff auf das Square: "+noSquareFoundException.getMessage()
            );
            return null;
        }
    }

    public UUID addMiningMachine(String name) {
        final Miningmaschine miningmaschine = new Miningmaschine(name);
        this.miningmaschineHashMap.put(miningmaschine.getId(),miningmaschine);
        return miningmaschine.getId();
    }

    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        try{
            final Miningmaschine miningMaschine = this.miningmaschineHashMap.get(miningMachineId);
            final Command command = CommandService.stringToCommand(commandString);
            switch (command.getDirection()){
                case EN:
                case TR:
                    return CommandService.commandWithUUID(command, miningMaschine, this);
                case NORTH:
                case WEST:
                case SOUTH:
                case EAST:
                    return CommandService.commandWithPower(command, miningMaschine, this);
                default:
                    throw new IllegalDirectionException(command.getDirection().toString());
            }
        }catch (IllegalDirectionException | IllegalDecisionException e){
            System.out.println(e);
            return false;
        }

    }

    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        try {
            return this.miningmaschineHashMap.get(miningMachineId).getPosition().getPlacedOnFieldId();
        } catch (MiningmaschineNotSpawnedException miningmaschineNotSpawnedException) {
            miningmaschineNotSpawnedException.printStackTrace();
        }
        return null;
    }

    public String getCoordinates(UUID miningMachineId) {
        try {
            return this.miningmaschineHashMap.get(miningMachineId).getPosition().getCoordinate().toString();
        } catch (MiningmaschineNotSpawnedException miningmaschineNotSpawnedException) {
            miningmaschineNotSpawnedException.printStackTrace();
        }
        return null;
    }



    @Override
    public HashMap<UUID, Miningmaschine> getMiningmaschineHashMap() {
        return this.miningmaschineHashMap;
    }

    @Override
    public HashMap<UUID, Field> getFieldHashMap() {
        return this.fieldHashMap;
    }
}
