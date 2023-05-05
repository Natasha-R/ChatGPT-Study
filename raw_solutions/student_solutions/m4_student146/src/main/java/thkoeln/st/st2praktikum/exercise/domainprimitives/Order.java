package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@Embeddable
@Setter
@NoArgsConstructor
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Order(OrderType orderType, Integer numberOfSteps) {
        if (!Pattern.matches("\\d+", numberOfSteps.toString()))
            throw new RuntimeException("The number of steps has to be positive!");
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    public static Order fromString(String orderString) {
        if (!Pattern.matches("\\[\\w{2},(.*)-?]", orderString))
            throw new RuntimeException("Wrong format! [command(2 letters long),value]");
        String[] orderSplit = orderString.split(",");
        String order = orderSplit[0].replace("[", "");
        String value = orderSplit[1].replace("]", "");
        Order order1 = new Order();
        switch (order) {
            case "no":
                order1.orderType = OrderType.NORTH;
                order1.numberOfSteps = Integer.parseInt(value);
                break;
            case "so":
                order1.orderType = OrderType.SOUTH;
                order1.numberOfSteps = Integer.parseInt(value);
                break;
            case "ea":
                order1.orderType = OrderType.EAST;
                order1.numberOfSteps = Integer.parseInt(value);
                break;
            case "we":
                order1.orderType = OrderType.WEST;
                order1.numberOfSteps = Integer.parseInt(value);
                break;
            case "en":
                order1.orderType = OrderType.ENTER;
                order1.gridId = UUID.fromString(value);
                break;
            case "tr":
                order1.orderType = OrderType.TRANSPORT;
                order1.gridId = UUID.fromString(value);
                break;
            default:
                throw new RuntimeException();
        }
        if (order1.numberOfSteps != null && !Pattern.matches("\\d+", order1.numberOfSteps.toString()))
            throw new RuntimeException("The number of steps has to be positive!");
        return order1;
    }

}
