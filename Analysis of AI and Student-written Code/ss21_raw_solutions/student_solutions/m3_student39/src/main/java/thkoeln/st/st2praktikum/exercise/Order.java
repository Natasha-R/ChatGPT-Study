package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        if(numberOfSteps<0)
            throw new RuntimeException("Steps are negative");
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no,3], [en,<uuid>], [tr,<uuid>]
     */
    public Order(String orderString) {
        if(!checkOrderString(orderString))
            throw new RuntimeException("Invalid order string");

        if(orderString.startsWith("[en") || orderString.startsWith("[tr"))
            gridId = UUID.fromString(orderString.substring(4,40));
        else
            numberOfSteps = Vector2D.decodeNumbers(orderString).get(0);

        switch(orderString.substring(1,3)){
            case "en":  orderType = OrderType.ENTER; break;
            case "tr":  orderType = OrderType.TRANSPORT; break;
            case "no":  orderType = OrderType.NORTH; break;
            case "we":  orderType = OrderType.WEST; break;
            case "ea":  orderType = OrderType.EAST; break;
            case "so":  orderType = OrderType.SOUTH; break;
        }
    }

    public static boolean checkOrderString(String orderString){
        if(orderString.charAt(0)=='[' && orderString.charAt(orderString.length()-1)==']'){
            if(orderString.startsWith("en,", 1) || orderString.startsWith("tr,", 1)) {
                return orderString.length() == 41;
            }
            else if(orderString.startsWith("no,", 1) || orderString.startsWith("we,", 1) ||
                    orderString.startsWith("ea,", 1) || orderString.startsWith("so,", 1)){
                for(int i=5;i<orderString.length()-1;i++){
                    if(!Character.isDigit(orderString.charAt(i))) return false;
                }
                return true;
            }
        }
        return false;
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
