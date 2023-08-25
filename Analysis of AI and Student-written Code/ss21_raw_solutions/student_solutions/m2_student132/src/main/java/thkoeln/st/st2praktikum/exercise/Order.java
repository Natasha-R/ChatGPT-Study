package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        if (numberOfSteps < 0) throw new IllegalArgumentException("Negative number of steps are not allowed.");
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
        String cleanCommandString = orderString.replaceFirst("\\[", "");
        cleanCommandString = cleanCommandString.replaceFirst("]", "");
        String[] parts = cleanCommandString.split(",");

        switch (parts[0]) {
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(parts[1]);
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(parts[1]);
                break;
            case "no":
                this.orderType = OrderType.NORTH;
                if (Integer.parseInt(parts[1]) < 0) throw new IllegalArgumentException("Negative number of steps are not allowed.");
                this.numberOfSteps = Integer.valueOf(parts[1]);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                if (Integer.parseInt(parts[1]) < 0) throw new IllegalArgumentException("Negative number of steps are not allowed.");
                this.numberOfSteps = Integer.valueOf(parts[1]);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                if (Integer.parseInt(parts[1]) < 0) throw new IllegalArgumentException("Negative number of steps are not allowed.");
                this.numberOfSteps = Integer.valueOf(parts[1]);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                if (Integer.parseInt(parts[1]) < 0) throw new IllegalArgumentException("Negative number of steps are not allowed.");
                this.numberOfSteps = Integer.valueOf(parts[1]);
                break;
            default:
                throw new IllegalArgumentException("String " + orderString + " not formatted correctly");
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
