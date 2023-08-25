package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.WrongOrderException;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) throws WrongOrderException {
        if(numberOfSteps>= 0) {


            this.orderType = orderType;
            this.numberOfSteps = numberOfSteps;
        } else {
            throw new WrongOrderException(numberOfSteps.toString());
        }
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) throws WrongOrderException{
        String newCommandRaw = orderString.substring(1, orderString.length() -1);
            String[] commandParts = newCommandRaw.split(",");
            OrderType order = null;
                order = OrderService.stringToOrderType(commandParts[0]);
                switch (order){
                case NORTH:
                case EAST:
                case WEST:
                case SOUTH:
                    if(Integer.parseInt(commandParts[1]) > 0 && Integer.parseInt(commandParts[1] ) >= 0){
                        this.orderType = order;
                        this.numberOfSteps = Integer.parseInt(commandParts[1]);
                        this.gridId = null;
                        break;
                    }else{
                        throw new WrongOrderException(orderString);
                    }
                case ENTER:
                case TRANSPORT:
                    this.orderType = order;
                    this.numberOfSteps = null;
                    this.gridId = UUID.fromString(commandParts[1]);
                    break;
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
