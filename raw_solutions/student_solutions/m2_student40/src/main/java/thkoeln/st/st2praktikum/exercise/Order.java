package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    private OrderType getOrderType(String typeString){
        switch (typeString){
            case "en":{
                return OrderType.ENTER;
            }
            case "tr":{
                return OrderType.TRANSPORT;
            }
            case "we":{
                return OrderType.WEST;
            }
            case "ea":{
                return OrderType.EAST;
            }
            case "so":{
                return OrderType.SOUTH;
            }
            case "no":{
                return OrderType.NORTH;
            }
        }

        throw new RuntimeException("Order type invalid");
    }

    private void parseOrderString(String orderString){
        orderString = orderString.replace("[", "");
        orderString = orderString.replace("]", "");
        String[] explodedCommand = orderString.split(",");

        orderType = getOrderType(explodedCommand[0]);
        switch (orderType){
            case ENTER:
            case TRANSPORT:{
                gridId = UUID.fromString(explodedCommand[1]);
                break;
            }
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:{
                numberOfSteps = Integer.parseInt(explodedCommand[1]);
                if( numberOfSteps < 0 ){
                    throw new RuntimeException("number of steps can not be negative");
                }
                break;
            }
        }
    }


    public Order(OrderType orderType, Integer numberOfSteps) {

        if( numberOfSteps < 0 ){
            throw new RuntimeException("numberOfSteps can not be negative");
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
        if( !orderString.matches("^\\[[a-zA-Z][a-zA-Z],(.*)\\]$")){
            throw new RuntimeException("Invalid order string");
        }

        parseOrderString(orderString);
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
