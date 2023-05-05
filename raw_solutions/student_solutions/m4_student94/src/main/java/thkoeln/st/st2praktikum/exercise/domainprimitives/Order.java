package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
public class Order {
    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID spaceId;

    public Order() { }

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        checkIfNumberOfStepNegative();
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.spaceId = gridId;
    }
    
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) { this.orderType=orderType; }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }
    public void setNumberOfSteps(Integer numbers) { this.numberOfSteps=numbers; }


    public UUID getSpaceId() {
        return spaceId;
    }
    public void setSpaceId(UUID gridid) {
        this.spaceId=gridid;
    }

    public static Order fromString(String orderString ) {
       return new Order(orderString);
    }

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
    public void checkIfNumberOfStepNegative() throws RuntimeException{
        if (numberOfSteps<0) throw new RuntimeException();
    }
}
