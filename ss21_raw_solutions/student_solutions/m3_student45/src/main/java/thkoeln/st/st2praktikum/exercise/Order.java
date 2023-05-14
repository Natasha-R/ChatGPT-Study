package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;
import thkoeln.st.st2praktikum.exercise.Exception.InvalidOrderException;

import java.util.UUID;

public class Order {

    private final OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new InvalidOrderException("The number of steps can't be negative");

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
        Pair<String, String> commandString = getStringCommandPair(orderString);
        String type = commandString.getLeft();
        String content = commandString.getRight();

        switch (type) {
            case "no":
                orderType = OrderType.NORTH;
                numberOfSteps = Integer.parseInt(content);
                break;

            case "ea":
                orderType = OrderType.EAST;
                numberOfSteps = Integer.parseInt(content);
                break;

            case "we":
                orderType = OrderType.WEST;
                numberOfSteps = Integer.parseInt(content);
                break;

            case "so":
                orderType = OrderType.SOUTH;
                numberOfSteps = Integer.parseInt(content);
                break;

            case "tr":
                orderType = OrderType.TRANSPORT;
                gridId = UUID.fromString(content);
                break;

            case "en":
                orderType = OrderType.ENTER;
                gridId = UUID.fromString(content);
                break;

            default:
                throw new InvalidOrderException("The parameter \"orderString\" doesn't represent an order.");
        }
    }

    private Pair<String, String> getStringCommandPair(String commandString) {
        String str = commandString
                .replace("[", "")
                .replace("]", "");
        String[] command = str.split(",");

        return Pair.of(command[0], command[1]);
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
