package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;


@Embeddable
public class Order {

    @Getter
    private OrderType orderType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;
    
    protected Order() {
        
    }

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

    public static Order fromString(String orderString ) {
        if(validateString(orderString)){
            String[] orderStringArray = orderString.replaceAll("[\\[\\]]", "").split(",");
            OrderType currentOrder = getOrderTypeFromString(orderStringArray[0]);

            if(isDiretion(currentOrder)){
                Integer numberOfSteps = getNumberOfStepsFromString(orderStringArray[1]);
                if(numberOfSteps > 0) {
                    Order order = new Order(currentOrder, numberOfSteps);
                    return order;
                }else{
                    throw new RuntimeException("Number of steps must be greater than zero! Your number of steps: " + numberOfSteps);
                }
            }else if(isCommand(currentOrder)){
                Order order = new Order(currentOrder, getGridIdFromString(orderStringArray[1]));
                return order;
            }else{
                throw new RuntimeException("OrderString not valid! Your orderString: " + orderString);
            }
        }else{
            throw new RuntimeException("OrderString not valid! Your orderString: " + orderString);
        }
    }


    private static boolean isDiretion(OrderType order){
        return (order == OrderType.NORTH ||
                order == OrderType.EAST ||
                order == OrderType.SOUTH ||
                order == OrderType.WEST);
    }

    private static boolean isCommand(OrderType order){
        return (order == OrderType.TRANSPORT ||
                order == OrderType.ENTER);
    }

    private static OrderType getOrderTypeFromString(String orderType){
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

    private static UUID getGridIdFromString(String orderString){
        try {
            return UUID.fromString(orderString);
        }catch(Exception e){
            throw new RuntimeException("Error when parsing UUID-String in OrderClass! Your UUID-string: " + orderString);
        }
    }

    private static Integer getNumberOfStepsFromString(String numberOfStepsString){
        try {
            return Integer.parseInt(numberOfStepsString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse NumberOfSteps to Integer in OrderClass! Your numberOfStepsString: " + numberOfStepsString);
        }
    }

    private static boolean validateString(String input){
        return input.matches("\\[\\w+\\,[\\w-]+\\]");
    }
}


/*
    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }
 */