package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        if(numberOfSteps<0){
            throw new RuntimeException("negative Zahl");
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


        String firstCommand = orderString.substring(1, 3);
        switch (firstCommand){
            case "en": this.orderType=OrderType.ENTER;
                break;
            case "tr":this.orderType=OrderType.TRANSPORT;
                break;
            case "no":this.orderType=OrderType.NORTH;
                break;
            case "ea":this.orderType=OrderType.EAST;
                break;
            case"so":this.orderType=OrderType.SOUTH;
                break;
            case "we":this.orderType=OrderType.WEST;
                break;


        }
    String secondCommand = (orderString.substring(4, orderString.length() - 1));

        if(secondCommand.length()>1) {
            this.gridId = UUID.fromString(secondCommand);
        }else{
            this.numberOfSteps= Integer.parseInt(secondCommand);

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
