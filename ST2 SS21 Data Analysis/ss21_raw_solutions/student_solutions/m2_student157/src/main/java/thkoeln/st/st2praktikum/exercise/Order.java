package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    private String INITIALISATION_ORDER = "en";
    private String TRAVEL_ORDER = "tr";


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;

        if(numberOfSteps < 0) {
            throw new RuntimeException();
        }
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        final String regex = "\\[(.{2}),(.*)\\]";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(orderString);

        String orderType = "";
        String orderOption = "";
        while (matcher.find()) {
            orderType = matcher.group(1);
            orderOption = matcher.group(2);
        }

        if(orderType.equals(INITIALISATION_ORDER)) {
            this.orderType = OrderType.ENTER;
            this.gridId = UUID.fromString(orderOption);
        } else if(orderType.equals(TRAVEL_ORDER)) {
            this.orderType = OrderType.TRANSPORT;
            this.gridId = UUID.fromString(orderOption);
        } else {
            this.orderType = directionToOrderType(orderType);
            this.numberOfSteps = Integer.parseInt(orderOption);
            if(numberOfSteps < 0) {
                throw new RuntimeException();
            }
        }
    }

    private OrderType directionToOrderType(String orderDirection) {
        switch (orderDirection) {
            case "we":
                return OrderType.WEST;
            case "ea":
                return OrderType.EAST;
            case "so":
                return OrderType.SOUTH;
            case "no":
                return OrderType.NORTH;
        }
        throw new RuntimeException("Not a supported direction: " + orderDirection);
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

    public boolean isInitialisationCommand() {
        return this.orderType == OrderType.ENTER;
    }

    public boolean isTransportCommand() {
        return this.orderType == OrderType.TRANSPORT;
    }

    public boolean isMoveCommand() {
        return this.orderType == OrderType.WEST || this.orderType == OrderType.EAST || this.orderType == OrderType.SOUTH || this.orderType == OrderType.NORTH;
    }
}
