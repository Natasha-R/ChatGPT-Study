package thkoeln.st.st2praktikum.exercise.world2;

import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;
import thkoeln.st.st2praktikum.exercise.world2.field.Field;
import thkoeln.st.st2praktikum.exercise.world2.miningMaschine.Miningmaschine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
public class World implements Cloud {

    private final HashMap<UUID, Field> fields = new HashMap<>();
    private final HashMap<UUID, Miningmaschine> miningmaschines = new HashMap<>();


    public UUID addField(Integer height, Integer width) {
        final Field field = new Field(height, width);
        this.fields.put(field.getFieldId(), field);
        return field.getFieldId();
    }

    public void addBarrier(UUID fieldId, Barrier barrier){
        this.fields.get(fieldId).addBarrier(barrier);
    }

    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        final Connection connection = new Connection(sourceFieldId,sourcePoint,destinationFieldId,destinationPoint);
        this.fields.get(sourceFieldId).addConnection(connection);
        return connection.getConnectionId();
    }

    public UUID addMiningMachine(String name){
        final Miningmaschine miningmaschine = new Miningmaschine(name);
        this.miningmaschines.put(miningmaschine.getMiningmaschineId(), miningmaschine);
        return miningmaschine.getMiningmaschineId();
    }


    public Boolean executeCommand(UUID miningMachineId, Order order){
        try{
            return this.miningmaschines.get(miningMachineId).executeCommand(order, this);
        }catch(RuntimeException exception){
            exception.printStackTrace();
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
        return false;
    }


    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return this.miningmaschines.get(miningMachineId).getFieldId();
    }

    public Point getPoint(UUID miningMachineId){
        return this.miningmaschines.get(miningMachineId).getPoint();
    }

    @Override
    public HashMap<UUID, Miningmaschine> getMiningmaschines() {
        return this.miningmaschines;
    }

    @Override
    public HashMap<UUID, Field> getFields() {
        return this.fields;
    }
}
