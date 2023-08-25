package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Order {

    private final OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        setNumberOfSteps(numberOfSteps);
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        if (orderString.charAt(0) != '[' || orderString.charAt(orderString.length()-1) != ']') throw new RuntimeException("Orders should have the form \"[no/ea/so/we/en/tr,direction/UUID]\"!");
        orderString = orderString.substring(1, orderString.length()-1);
        String[] orderAsStringArray = orderString.split(",");
        if (orderAsStringArray.length != 2) throw new RuntimeException("Orders should have the form \"[no/ea/so/we/en/tr,direction/UUID]\"!");
        switch (orderAsStringArray[0]) {
            case "no":
                this.orderType = OrderType.NORTH;
                setNumberOfSteps(orderAsStringArray[1]);
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                setNumberOfSteps(orderAsStringArray[1]);
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                setNumberOfSteps(orderAsStringArray[1]);
                break;
            case "we":
                this.orderType = OrderType.WEST;
                setNumberOfSteps(orderAsStringArray[1]);
                break;
            case "en":
                this.orderType = OrderType.ENTER;
                this.gridId = UUID.fromString(orderAsStringArray[1]);
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                this.gridId = UUID.fromString(orderAsStringArray[1]);
                break;
            default:
                throw new RuntimeException(orderAsStringArray[0]+" is not a valid direction! Valid directions are Only \"no\", \"ea\", \"so\", \"we\", \"en\" & \"tr\"!");
        }
    }

    private void setNumberOfSteps(String stepsAsString) {
        if (stepsAsString.length() < 1) throw new RuntimeException(stepsAsString+" is not a valid number of steps!");
        int steps = Integer.parseInt(stepsAsString);
        if (steps <= 0) throw new RuntimeException("Negative count of Steps aren't allowed!");
        this.numberOfSteps = steps;
    }

    private void setNumberOfSteps(int steps) {
        if (steps <= 0) throw new RuntimeException("Negative count of Steps aren't allowed!");
        this.numberOfSteps = steps;
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
