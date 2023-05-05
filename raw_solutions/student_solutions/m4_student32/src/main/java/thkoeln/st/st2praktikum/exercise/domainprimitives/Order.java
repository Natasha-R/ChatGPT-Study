package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
@Getter
@Setter
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Order() {
        
    }

    public Order(OrderType orderType, Integer numberOfSteps) {
        if (numberOfSteps < 0){
            throw new RuntimeException("numberOfSteps must be positive");
        }
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
        
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
        String[] commands = orderString.substring(1, orderString.length() - 1).split(",");
        switch (commands[0]) {
            case "no":
                return new Order(OrderType.NORTH, Integer.parseInt(commands[1]));
            case "ea":
                return new Order(OrderType.EAST, Integer.parseInt(commands[1]));
            case "so":
                return new Order(OrderType.SOUTH, Integer.parseInt(commands[1]));
            case "we":
                return new Order(OrderType.WEST, Integer.parseInt(commands[1]));
            case "en":
                return new Order(OrderType.ENTER, UUID.fromString(commands[1]));
            case "tr":
                return new Order(OrderType.TRANSPORT, UUID.fromString(commands[1]));
            default:
                throw new RuntimeException("Invalid Order");
        }
    }
    
}
