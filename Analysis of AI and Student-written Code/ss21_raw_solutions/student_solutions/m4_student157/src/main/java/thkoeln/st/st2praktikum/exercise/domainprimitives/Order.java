package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Setter
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    private static String INITIALISATION_ORDER = "en";
    private static String TRAVEL_ORDER = "tr";

    protected Order() {

    }

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        if(numberOfSteps < 0) throw new RuntimeException();
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;

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

    public static Order fromString(String orderString ) {
        final String regex = "\\[(.{2}),(.*)\\]";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(orderString);
        OrderType parsedOrderType;
        UUID parsedGridId;
        int parsedNumberOfSteps;

        String orderType = "";
        String orderOption = "";
        while (matcher.find()) {
            orderType = matcher.group(1);
            orderOption = matcher.group(2);
        }

        if(orderType.equals(Order.INITIALISATION_ORDER)) {
            parsedOrderType = OrderType.ENTER;
            parsedGridId = UUID.fromString(orderOption);
        } else if(orderType.equals(TRAVEL_ORDER)) {
            parsedOrderType = OrderType.TRANSPORT;
            parsedGridId = UUID.fromString(orderOption);
        } else {
            parsedOrderType = directionToOrderType(orderType);
            parsedNumberOfSteps = Integer.parseInt(orderOption);
            if(parsedNumberOfSteps < 0) {
                throw new RuntimeException();
            }
            return new Order(parsedOrderType, parsedNumberOfSteps);
        }

        return new Order(parsedOrderType, parsedGridId);
    }

    private static OrderType directionToOrderType(String orderDirection) {
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
