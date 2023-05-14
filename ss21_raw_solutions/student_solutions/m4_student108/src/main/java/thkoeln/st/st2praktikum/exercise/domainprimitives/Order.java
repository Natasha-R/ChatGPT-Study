package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Order {

    @Getter
    private OrderType orderType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;

    
    protected Order() {}

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        setNumberOfSteps(numberOfSteps);
        
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
        
    }

    private void setNumberOfSteps(int steps) {
        if (steps <= 0) throw new RuntimeException("Negative count of Steps aren't allowed!");
        this.numberOfSteps = steps;
    }
    
    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public static Order fromString(String orderString ) {
        if (orderString.charAt(0) != '[' || orderString.charAt(orderString.length()-1) != ']') throw new RuntimeException("Orders should have the form \"[no/ea/so/we/en/tr,direction/UUID]\"!");
        orderString = orderString.substring(1, orderString.length()-1);
        String[] orderAsStringArray = orderString.split(",");
        if (orderAsStringArray.length != 2) throw new RuntimeException("Orders should have the form \"[no/ea/so/we/en/tr,direction/UUID]\"!");
        switch (orderAsStringArray[0]) {
            case "no":
                return new Order(OrderType.NORTH, Integer.parseInt(orderAsStringArray[1]));
            case "ea":
                return new Order(OrderType.EAST, Integer.parseInt(orderAsStringArray[1]));
            case "so":
                return new Order(OrderType.SOUTH, Integer.parseInt(orderAsStringArray[1]));
            case "we":
                return new Order(OrderType.WEST, Integer.parseInt(orderAsStringArray[1]));
            case "en":
                return new Order(OrderType.ENTER, UUID.fromString(orderAsStringArray[1]));
            case "tr":
                return new Order(OrderType.TRANSPORT, UUID.fromString(orderAsStringArray[1]));
            default:
                throw new RuntimeException(orderAsStringArray[0]+" is not a valid direction! Valid directions are Only \"no\", \"ea\", \"so\", \"we\", \"en\" & \"tr\"!");
        }
    }
    
}
