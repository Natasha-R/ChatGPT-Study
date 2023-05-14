package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        if (this.numberOfSteps < 0) throw new RuntimeException();
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        Pattern p = Pattern.compile("\\[([a-z]{2}),([\\d\\-A-Fa-f]*)\\]");
        Matcher matcher = p.matcher(orderString);
        //MaintenanceDroid affectedDroid = ship.findDroidById(droidId);

        if (matcher.matches()) {
            String commandTypeOrDirection = matcher.group(1).toLowerCase();

            if (commandTypeOrDirection.equals("tr")) {
                orderType = OrderType.TRANSPORT;
                gridId = UUID.fromString(matcher.group(2));
            } else if (commandTypeOrDirection.equals("en")) {
                orderType = OrderType.ENTER;
                gridId = UUID.fromString(matcher.group(2));
            } else {
                orderType = parseDirection(commandTypeOrDirection);
                numberOfSteps = Integer.parseInt(matcher.group(2));
                if (numberOfSteps < 0) throw new RuntimeException();
            }
        } else {
            throw new ParseException("Could not parse Command string");
        }

    }

    private OrderType parseDirection(String direction) {
        switch (direction) {
            case "ea": return OrderType.EAST;
            case "we": return OrderType.WEST;
            case "no": return OrderType.NORTH;
            case "so": return OrderType.SOUTH;
        }
        throw new ParseException("Unknown Order Type");
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
