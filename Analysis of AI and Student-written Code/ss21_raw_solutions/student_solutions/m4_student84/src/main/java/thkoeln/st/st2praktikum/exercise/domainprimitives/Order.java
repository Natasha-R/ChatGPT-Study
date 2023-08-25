package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Embeddable
public class Order {
    @Getter
    private OrderType orderType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;

    public Order(OrderType orderType, Integer numberOfSteps) {
        if(numberOfSteps < 0) {
            throw new RuntimeException("numberOfSteps must be positive");
        }

        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    public static Order fromString(String orderString) {
        return new Order(orderString);
    }
    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        String[] orderStrings = orderString.replace("[", "").replace("]", "").split(",");
        orderType = OrderType.fromString(orderStrings[0]);

        switch (orderType) {
            case TRANSPORT:
                System.out.println("Transport");
                gridId = UUID.fromString(orderStrings[1]);
                break;
            case ENTER:
                System.out.println("Enter");
                gridId = UUID.fromString(orderStrings[1]);
                break;
            default:
                System.out.println("Move");
                numberOfSteps = Integer.parseInt(orderStrings[1]);
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
}
