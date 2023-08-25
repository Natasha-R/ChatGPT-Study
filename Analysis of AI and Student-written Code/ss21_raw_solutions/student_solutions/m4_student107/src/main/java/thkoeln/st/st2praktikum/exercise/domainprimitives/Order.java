package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
import org.modelmapper.*;



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
        if (! (numberOfSteps >= 0)) throw new RuntimeException("Number of Steps was negative");

        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        String[] parameter = orderString.replace("[", "").replace("]","").split(",");

        switch (parameter[0]) {
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(parameter[1]);
                break;
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(parameter[1]);
                break;
            case "no":
                this.orderType = OrderType.NORTH;
                if (!(Integer.parseInt(parameter[1]) >= 0)) throw new RuntimeException("Number of Steps was negative");
                this.numberOfSteps = Integer.parseInt(parameter[1]);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                if (!(Integer.parseInt(parameter[1]) >= 0)) throw new RuntimeException("Number of Steps was negative");
                this.numberOfSteps = Integer.parseInt(parameter[1]);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                if (!(Integer.parseInt(parameter[1]) >= 0)) throw new RuntimeException("Number of Steps was negative");
                this.numberOfSteps = Integer.parseInt(parameter[1]);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                if (!(Integer.parseInt(parameter[1]) >= 0)) throw new RuntimeException("Number of Steps was negative");
                this.numberOfSteps = Integer.parseInt(parameter[1]);
                break;
            default:
                throw new RuntimeException("Invalid Command");

        }
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
        return new Order(orderString);
    }
    
}
