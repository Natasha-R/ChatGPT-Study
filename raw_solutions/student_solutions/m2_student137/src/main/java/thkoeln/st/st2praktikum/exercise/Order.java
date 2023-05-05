package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    // movement oder
    public Order(OrderType orderType, Integer numberOfSteps) {

        if(numberOfSteps < 0) {
            throw  new NumberFormatException();
        } else {
            this.numberOfSteps = numberOfSteps;
        }

        this.orderType = orderType;
    }

    // enter or transport order
    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {

        // split start and end values at the comma
        String[] Split = orderString.split(",");
        ArrayList<String> totalSplit = new ArrayList<>();

        // store split values in new arraylist
        totalSplit.add(Split[0]); // (NUMBER
        totalSplit.add(Split[1]); // NUMBER)

        // split second value again to get rid of last bracket
        String[] secondValueSplit = totalSplit.get(1).split("]");

        // devine the order type and change it depending on the given value. if non is matching: throw exception
        OrderType orderTypeTemp = OrderType.ENTER;

        switch (totalSplit.get(0).substring(1)) {

            case "en": orderTypeTemp = OrderType.ENTER;
            break;

            case "tr": orderTypeTemp = OrderType.TRANSPORT;
            break;

            case "no": orderTypeTemp = OrderType.NORTH;
            break;

            case "ea": orderTypeTemp = OrderType.EAST;
            break;

            case "so": orderTypeTemp = OrderType.SOUTH;
            break;

            case "we": orderTypeTemp = OrderType.WEST;
            break;

            default: throw  new NumberFormatException();
        }

        // set value for steps / grid depending on the order type
        if (secondValueSplit[0].length() < 9) {


            if(Integer.parseInt(secondValueSplit[0]) < 0) {
                throw  new NumberFormatException();
            } else {
                this.numberOfSteps = Integer.parseInt(secondValueSplit[0]);
            }

        } else {

            this.gridId = UUID.fromString(secondValueSplit[0]);
        }
        this.orderType = orderTypeTemp;
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
