package thkoeln.st.st2praktikum.exercise;


import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.checkStepsNegativ(numberOfSteps);
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) throws IllegalArgumentException {
        String orderString_raw = orderString.substring(1, orderString.length() - 1);
        String[] orderString_array = orderString_raw.split(",");
        switch (orderString_array[0]){
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(orderString_array[1]);
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(orderString_array[1]);
                break;
            case "no":
                this.orderType = OrderType.NORTH;
                this.checkStepsNegativ(Integer.parseInt(orderString_array[1]));
                this.numberOfSteps = Integer.parseInt(orderString_array[1]);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                this.checkStepsNegativ(Integer.parseInt(orderString_array[1]));
                this.numberOfSteps = Integer.parseInt(orderString_array[1]);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                this.checkStepsNegativ(Integer.parseInt(orderString_array[1]));
                this.numberOfSteps = Integer.parseInt(orderString_array[1]);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                this.checkStepsNegativ(Integer.parseInt(orderString_array[1]));
                this.numberOfSteps = Integer.parseInt(orderString_array[1]);
                break;
            default:
                throw new IllegalArgumentException(orderString_array[0]);
        }
    }

    private void checkStepsNegativ(Integer steps) throws IllegalArgumentException{
        if(steps < 0){
            throw new IllegalArgumentException("Steps sind nEgaTiv");
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
