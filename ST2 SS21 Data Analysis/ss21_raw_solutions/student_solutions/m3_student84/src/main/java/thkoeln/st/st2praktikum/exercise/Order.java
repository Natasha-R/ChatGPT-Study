package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
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
