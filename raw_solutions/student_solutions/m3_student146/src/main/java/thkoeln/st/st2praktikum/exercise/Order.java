package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Pattern;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        if(!Pattern.matches("\\d+", numberOfSteps.toString()))
            throw new RuntimeException("The number of steps has to be positive!");
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
        if(!Pattern.matches("\\[\\w{2},(.*)-?]", orderString))
            throw new RuntimeException("Wrong format! [command(2 letters long),value]");
        String[] orderSplit = orderString.split(",");
        String order = orderSplit[0].replace("[", "");
        String value = orderSplit[1].replace("]", "");
        switch (order) {
            case "no":
                this.orderType = OrderType.NORTH;
                this.numberOfSteps = Integer.parseInt(value);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                this.numberOfSteps = Integer.parseInt(value);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                this.numberOfSteps = Integer.parseInt(value);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                this.numberOfSteps = Integer.parseInt(value);
                break;
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(value);
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(value);
                break;
            default:
                throw new RuntimeException();
        }
        if(numberOfSteps != null && !Pattern.matches("\\d+", numberOfSteps.toString()))
            throw new RuntimeException("The number of steps has to be positive!");
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
