package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


@Entity
public class Order  extends TidyUpRobotService  {

    @Id
    @Getter
    private UUID orderId = UUID.randomUUID() ;

    @Getter
    @Setter
    private OrderType orderType;
    @Getter
    @Setter
    private Integer numberOfSteps;
    @Getter
    @Setter
    private UUID gridId;
    @Getter
    @Setter
    private String orderString;
    @Getter
    @Setter
    private Boolean isValidOrder;


    public Order(){}

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        this.isValidOrder = isValidOrder(this);
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        this.orderString = orderString;
        this.isValidOrder = isValidOrder(this);
    }


    public OrderType getOrderType() {
        if (this.getOrderString().contains("so"))return OrderType.SOUTH;
        if (this.getOrderString().contains("no"))return OrderType.NORTH;
        if (this.getOrderString().contains("we"))return OrderType.WEST;
        if (this.getOrderString().contains("ea"))return OrderType.EAST;
        if (this.getOrderString().contains("en"))return OrderType.ENTER;
        if (this.getOrderString().contains("tr"))return OrderType.TRANSPORT;
        else return null;
    }

    public Integer getNumberOfSteps() {
        Integer NumberOfSteps;
        NumberOfSteps = Integer.parseInt(this.orderString.substring(4,5));
        return NumberOfSteps;
    }
    public String getOrderString(){
        return orderString;
    }

    public UUID getGridId() {
        return gridId;
    }

    public Boolean isValidOrder(Order order){
        Integer lengthOfString = order.getOrderString().length();

        String commandValue = order.getOrderString().substring(1,3);

        String moveValue = order.getOrderString().substring(4, getOrderString().length()-1);





        if (commandValue.equals("no")||commandValue.equals("we")|| commandValue.equals("so")||commandValue.equals("ea")){


            if (Integer.parseInt(moveValue)<0){
                throw new RuntimeException();
            }
            else if (lengthOfString.equals(6)){

                return true;
            }
            else throw new IllegalArgumentException("Invalid Order") ;

        }
        else if (commandValue.equals("en") || commandValue.equals("tr")){

            return true;
        }
        else throw new IllegalArgumentException("Invalid Order");
    }

}
