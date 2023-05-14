package thkoeln.st.st2praktikum.exercise.world2.miningMaschine;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;
import thkoeln.st.st2praktikum.exercise.world2.services.BarrierService;

import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;

public class Miningmaschine {

    @Id
    @Getter
    private final UUID miningmaschineId = UUID.randomUUID();

    @Getter
    private final String name;

    @Getter
    @Setter
    private UUID fieldId;

    @Getter
    @Setter
    private Point point;

    @Getter
    private Boolean spawned = false;

    public Miningmaschine(String name){
        this.name = name;
    }


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
                }
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
        //Für jede connection auf dem Feld wo der Droid steht...
        for (Map.Entry<UUID, Connection> connectionEntry : world.getFields().get(this.fieldId).getConnections().entrySet()) {
            //wenn das aktuelle Feld auf dem der Droid steht ein Connection Feld ist...
            if(connectionEntry.getValue().getSourcePoint().equals(this.point)){
                //Für jeden Droid auf der Welt
                for (Map.Entry<UUID, Miningmaschine> miningmaschineEntry : world.getMiningmaschines().entrySet()) {
                    //Wenn er gespawned ist...
                    if(miningmaschineEntry.getValue().getSpawned()){
                        //Wenn er auf dem connection destination feld steht...
                        if(miningmaschineEntry.getValue().getPoint().equals(connectionEntry.getValue().getDestinationPoint())){
                            return  false;
                        }else{
                            this.fieldId = connectionEntry.getValue().getDestinationFieldId();
                            this.point = connectionEntry.getValue().getDestinationPoint();
                            return true;
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("transport()");
    }

    private Boolean enterField(UUID fieldId, Cloud world) throws IllegalStateException {
        for (Map.Entry<UUID, Miningmaschine> miningmaschineEntry : world.getMiningmaschines().entrySet()) {
            if(!miningmaschineEntry.getKey().equals(this.miningmaschineId)){
                if(miningmaschineEntry.getValue().getSpawned()){
                    if(miningmaschineEntry.getValue().getPoint().equals(new Point(0,0))){
                        return false;
                    }else{
                        this.spawned = true;
                        this.point = new Point(0,0);
                        this.fieldId = fieldId;
                        return true;
                    }
                }else{
                    this.spawned = true;
                    this.point = new Point(0,0);
                    this.fieldId = fieldId;
                    return true;
                }
            }
        }
        throw new IllegalStateException("enterField()");
    }

    private void printStatus(){
        System.out.println("FieldId: "+this.fieldId);
        System.out.println("Point: "+this.point.toString());
        System.out.println("=========================");
    }
}
