package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


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

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        String[] commands = orderString.substring(1, orderString.length() - 1).split(",");
        switch (commands[0]) {
            case "no":
                this.orderType = OrderType.NORTH;
                this.numberOfSteps = Integer.parseInt(commands[1]);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                this.numberOfSteps = Integer.parseInt(commands[1]);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                this.numberOfSteps = Integer.parseInt(commands[1]);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                this.numberOfSteps = Integer.parseInt(commands[1]);
                break;
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(commands[1]);
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(commands[1]);
                break;
            default:
                throw new RuntimeException("Invalid Order");
        }
    }

}
