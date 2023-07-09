package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Entity
public class MiningMachine {

    @Id
    private final UUID miningmaschineId = UUID.randomUUID();

    private String name;

    @Setter
    private UUID fieldId;

    @Embedded
    @Setter
    private Point point;

    private Boolean spawned = false;

    public MiningMachine(String name){
        this.name = name;
    }

    protected MiningMachine(){}


    public Boolean executeCommand(Order order, Cloud world) {
        switch (order.getOrderType()){
            case NORTH:
            case SOUTH:
                return this.goVertikal(order.getNumberOfSteps(), world, order.getOrderType());
            case EAST:
            case WEST:
                return this.goHorizontal(order.getNumberOfSteps(), world, order.getOrderType());
            case TRANSPORT:
                return this.transport(order.getGridId(), world);
            case ENTER:
                return this.enterField(order.getGridId(), world);
        }
        throw new IllegalStateException("miningmaschine.executeCommand()");
    }

    private Boolean goHorizontal(Integer numberOfSteps, Cloud world, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++){
            this.printStatus();
            if(BarrierService.checkVerticalBarriers(this.fieldId, this.point, world, orderType)){
                if(!Cloud.checkMiningmaschinesInDirection(this.fieldId, this.point, world, orderType, this.miningmaschineId)){
                    switch (orderType){
                        case EAST:
                            this.point = new Point((this.point.getX()+1), this.point.getY());
                            break;
                        case WEST:
                            this.point = new Point((this.point.getX()-1), this.point.getY());
                            break;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private Boolean goVertikal(Integer numberOfSteps, Cloud world, OrderType orderType) {
        for (int i = 0; i < numberOfSteps; i++){
            this.printStatus();
            if(BarrierService.checkHorizontalBarriers(this.fieldId, this.point, world, orderType)){
                if(!Cloud.checkMiningmaschinesInDirection(this.fieldId, this.point, world, orderType, this.miningmaschineId)){
                    switch (orderType){
                        case NORTH:
                            this.point = new Point(this.point.getX(), (this.point.getY()+1));
                            break;
                        case SOUTH:
                            this.point = new Point(this.point.getX(), (this.point.getY()-1));
                            break;
                    }
                }
            }
        }
        return true;
    }

    private Boolean transport(UUID gridId, Cloud world) {

        AtomicReference<Boolean> result = new AtomicReference<>(false);
        //Für jede connection auf dem Feld wo der Droid steht...
        for (Map.Entry<UUID, Connection> connectionEntry : world.getFields().findById(this.fieldId).get().getConnections().entrySet()) {
            //wenn das aktuelle Feld auf dem der Droid steht ein Connection Feld ist...
            if(connectionEntry.getValue().getSourcePoint().equals(this.point)){
                //Für jeden Droid auf der Welt
                for (MiningMachine miningmaschineEntry : world.getMiningmaschines().findAll()) {//Wenn er gespawned ist...
                    if (miningmaschineEntry.getSpawned()) {
                        //Wenn er auf dem connection destination feld steht...
                        if (miningmaschineEntry.getPoint().equals(connectionEntry.getValue().getDestinationPoint())) {
                            result.set(false);
                            break;
                        } else {
                            this.fieldId = connectionEntry.getValue().getDestinationFieldId();
                            this.point = connectionEntry.getValue().getDestinationPoint();
                            result.set(true);
                            break;
                        }
                    }
                }
            }
        }
        return result.get();
    }

    private Boolean enterField(UUID fieldId, Cloud world) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        for (MiningMachine miningmaschineEntry : world.getMiningmaschines().findAll()) {
            if (!miningmaschineEntry.getMiningmaschineId().equals(this.miningmaschineId)) {
                if (miningmaschineEntry.getSpawned()) {
                    if (miningmaschineEntry.getPoint().equals(new Point(0, 0))) {
                        result.set(false);
                        break;
                    } else {
                        this.spawned = true;
                        this.point = new Point(0, 0);
                        this.fieldId = fieldId;
                        result.set(true);
                        break;
                    }
                } else {
                    this.spawned = true;
                    this.point = new Point(0, 0);
                    this.fieldId = fieldId;
                    result.set(true);
                    break;
                }
            }
        }
        return result.get();
    }

    private void printStatus(){
        System.out.println("FieldId: "+this.fieldId);
        System.out.println("Point: "+this.point.toString());
        System.out.println("=========================");
    }
}
