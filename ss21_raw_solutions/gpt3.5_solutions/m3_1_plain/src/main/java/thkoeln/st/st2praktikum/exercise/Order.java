package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    @Id
    private UUID gridId;

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    public Order(String orderString) throws InvalidOrderException {
        if (orderString == null || !orderString.matches("^\\[(no|we|so|ea|tr|en),\\s*(\\d+|<uuid>)\\]$")) {
            throw new InvalidOrderException("Invalid order string: " + orderString);
        }

        String[] tokens = orderString.substring(1, orderString.length() - 1).split(",");
        OrderType orderType = OrderType.valueOf(tokens[0].toUpperCase());

        if (orderType == OrderType.ENTER || orderType == OrderType.TRANSPORT) {
            UUID gridId;
            try {
                gridId = UUID.fromString(tokens[1].trim().substring(1, tokens[1].length() - 1));
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderException("Invalid UUID in order string: " + orderString);
            }
            this.gridId = gridId;
        } else {
            Integer numberOfSteps = Integer.valueOf(tokens[1].trim());
            this.numberOfSteps = numberOfSteps;
        }

        this.orderType = orderType;
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

    public static class InvalidOrderException extends RuntimeException {
        public InvalidOrderException(String message) {
            super(message);
        }
    }
}