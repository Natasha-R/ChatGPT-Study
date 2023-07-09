package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
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
        throw new UnsupportedOperationException();
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
