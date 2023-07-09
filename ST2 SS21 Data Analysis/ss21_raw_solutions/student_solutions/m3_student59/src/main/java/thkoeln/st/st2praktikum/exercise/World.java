package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class World implements Cloud {



    private final FieldRepository fields;
    private final MiningMachineRepository miningMachines;
    private final CategoriesRepository categories;


    @Autowired
    public World(
            FieldRepository fields,
            MiningMachineRepository miningMachines,
            CategoriesRepository categoriesRepository
    ){
        this.fields = fields;
        this.miningMachines = miningMachines;
        this.categories = categoriesRepository;
    }


    public UUID addField(Integer height, Integer width) {
        final Field field = new Field(height, width);
        this.fields.save(field);
        return field.getFieldId();
    }

    public void addBarrier(UUID fieldId, Barrier barrier){
        this.fields.findById(fieldId).get().addBarrier(barrier);
    }

    public UUID addTransportCategory(String category) {
        final Category categoryObj = new Category(category);
        this.categories.save(categoryObj);
        return categoryObj.getCategoryId();
    }

    public UUID addConnection(UUID transportCategoryId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        final Connection connection = new Connection(transportCategoryId, sourceFieldId,sourcePoint,destinationFieldId,destinationPoint);
        this.fields.findById(sourceFieldId).get().addConnection(connection);
        return connection.getConnectionId();
    }

    public UUID addMiningMachine(String name){
        final MiningMachine miningmaschine = new MiningMachine(name);
        this.miningMachines.save(miningmaschine);
        return miningmaschine.getMiningmaschineId();
    }


    public Boolean executeCommand(UUID miningMachineId, Order order){
        try{
            return this.miningMachines.findById(miningMachineId).get().executeCommand(order, this);
        }catch(RuntimeException exception){
            exception.printStackTrace();
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
        return false;
    }


    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return this.miningMachines.findById(miningMachineId).get().getFieldId();
    }

    public Point getPoint(UUID miningMachineId){
        return this.miningMachines.findById(miningMachineId).get().getPoint();
    }

    @Override
    public MiningMachineRepository getMiningmaschines() {
        return this.miningMachines;
    }

    @Override
    public FieldRepository getFields() {
        return this.fields;
    }
}
