package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Pattern;

public class Order {

    private OrderType OrderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.OrderType = orderType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new RuntimeException("Negative Schritte nicht zulaessig");
        }
    }

    public Order(OrderType orderType, UUID gridId) {
        this.OrderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {

        if (orderString == null) {
            throw new UnsupportedOperationException();
        }

        String[] parts = orderString.replaceAll("\\[|\\]", "").split(Pattern.quote(","));

        if (parts.length < 2) {
            throw new RuntimeException("Falscher Eingabestring");
        }


        if (validCommandAndValueCHECK(parts[0],parts[1])) {
            correctInit(parts);
        }

    }

    public OrderType getOrderType() {
        return OrderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }
    private boolean validCommandAndValueCHECK(String command, String value)
    {
        String uuidCheck[] = value.split("-");
        if (uuidCheck.length != 5 && (command == "tr" || command == "en")) {
            throw new IllegalArgumentException("gridId erwartet aber nicht bekommen");
        }
        if (uuidCheck.length == 5 && (command == "no" || command == "ea" || command == "so" || command == "we")) {
            throw new IllegalArgumentException("Steps erwartet aber UUID bekommen");
        }
        return true;

    }
    private void correctInit(String[] string)
    {
        switch (string[0]) {
            case "tr":
                this.OrderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(string[1]);
                break;

            case "en":
                this.OrderType = OrderType.ENTER;
                this.gridId = UUID.fromString(string[1]);
                break;

            case "no":
                this.OrderType = OrderType.NORTH;
                this.numberOfSteps = Integer.parseInt(string[1]);
                break;

            case "ea":
                this.OrderType = OrderType.EAST;
                this.numberOfSteps = Integer.parseInt(string[1]);
                break;

            case "so":
                this.OrderType = OrderType.SOUTH;
                this.numberOfSteps = Integer.parseInt(string[1]);
                break;

            case "we":
                this.OrderType = OrderType.WEST;
                this.numberOfSteps = Integer.parseInt(string[1]);
                break;

            default:
                throw new RuntimeException("Falscher orderType");
        }
    }
}