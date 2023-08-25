package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.assembler.Assembler;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;

import java.util.Map;
import java.util.UUID;

public class Order {


    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID spaceId;

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        checkIfNumberOfStepNegative();
    }

    public Order(OrderType orderType, UUID gridId) {
        this.spaceId = gridId;
        this.orderType = orderType;
        checkIfFieldIdExists();

    }
    public Order(){}

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */

    public Order(String orderString){
        String movement=orderString.substring(1,orderString.length()-1);
        String[] givenCommand=movement.split(",");

        switch(givenCommand[0]){
            case "no": orderType = OrderType.NORTH; break;
            case "so":  orderType = OrderType.SOUTH; break;
            case "we":  orderType = OrderType.WEST; break;
            case "ea":  orderType = OrderType.EAST;break;
            case "tr": orderType = OrderType.TRANSPORT; break;
            case "en":  orderType = OrderType.ENTER; break;
            default: throw new IllegalArgumentException("Illegal command: "+ orderString);
        }

        if( (orderType == OrderType.TRANSPORT||orderType == OrderType.ENTER)) {
            spaceId=UUID.fromString(givenCommand[1]);
        }
        else
            numberOfSteps= Integer.parseInt(givenCommand[1]);
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return spaceId;
    }

    public void checkIfNumberOfStepNegative() throws RuntimeException{
        if (numberOfSteps<0) throw new RuntimeException();
    }

    private void checkIfFieldIdExists() throws RuntimeException {
        for (Map.Entry<UUID, SpaceManageable> entry : Assembler.getSpaceList().entrySet()) {
            if (spaceId.equals(entry.getValue().getId())) {
                return;
            }
        }
        throw new RuntimeException();
    }
}
