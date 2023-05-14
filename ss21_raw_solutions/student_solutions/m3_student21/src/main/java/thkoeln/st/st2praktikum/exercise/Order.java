package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@NoArgsConstructor
@Embeddable
public class Order {

    @Getter
    private OrderType orderType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;

    public Order(OrderType orderType, Integer numberOfSteps) {
        if(isDiretion(orderType) && (numberOfSteps > 0)){
            this.orderType = orderType;
            this.numberOfSteps = numberOfSteps;
        }else{
            throw new RuntimeException("Expected Direction and positive number of Steps! Got orderType: " + orderType + " ,numberOfSteps: " + numberOfSteps);
        }
    }

    public Order(OrderType orderType, UUID gridId) {
        if(isCommand(orderType)){
            this.orderType = orderType;
            this.gridId = gridId;
        }else{
            throw new RuntimeException("Expected Command OrderType! Your orderType: " + orderType);
        }
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
        if(validateString(orderString)){
            String[] orderStringArray = orderString.replaceAll("[\\[\\]]", "").split(",");
            OrderType currentOrder = getOrderTypeFromString(orderStringArray[0]);

            if(isDiretion(currentOrder)){
                Integer numberOfSteps = getNumberOfStepsFromString(orderStringArray[1]);
                if(numberOfSteps > 0) {
                    this.orderType = currentOrder;
                    this.numberOfSteps = numberOfSteps;
                }else{
                    throw new RuntimeException("Number of steps must be greater than zero! Your number of steps: " + numberOfSteps);
                }
            }else if(isCommand(currentOrder)){
                this.gridId = getGridIdFromString(orderStringArray[1]);
                this.orderType = currentOrder;
            }else{
                throw new RuntimeException("OrderString not valid! Your orderString: " + orderString);
            }
        }else{
            throw new RuntimeException("OrderString not valid! Your orderString: " + orderString);
        }
    }

    private boolean isDiretion(OrderType order){
        return (order == OrderType.NORTH ||
                order == OrderType.EAST ||
                order == OrderType.SOUTH ||
                order == OrderType.WEST);
    }

    private boolean isCommand(OrderType order){
        return (order == OrderType.TRANSPORT ||
                order == OrderType.ENTER);
    }

    private OrderType getOrderTypeFromString(String orderType){
        switch(orderType) {
            case "no":
                return OrderType.NORTH;
            case "ea":
                return OrderType.EAST;
            case "so":
                return OrderType.SOUTH;
            case "we":
                return OrderType.WEST;
            case "en":
                return OrderType.ENTER;
            case "tr":
                return OrderType.TRANSPORT;
            default:
                throw new RuntimeException("Not an OrderType! Your String: " + orderType);
        }
    }

    private UUID getGridIdFromString(String orderString){
        try {
            return UUID.fromString(orderString);
        }catch(Exception e){
            throw new RuntimeException("Error when parsing UUID-String in OrderClass! Your UUID-string: " + orderString);
        }
    }

    private Integer getNumberOfStepsFromString(String numberOfStepsString){
        try {
            return Integer.parseInt(numberOfStepsString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse NumberOfSteps to Integer in OrderClass! Your numberOfStepsString: " + numberOfStepsString);
        }
    }

    private boolean validateString(String input){
        return input.matches("\\[\\w+\\,[\\w-]+\\]");
    }
}
