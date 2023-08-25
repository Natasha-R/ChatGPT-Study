package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import javax.persistence.Embeddable;
import java.util.UUID;


import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Order {

    private OrderType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Order(OrderType commandType, Integer numberOfSteps) {
        if(numberOfSteps >= 0) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }else throw new RuntimeException("It't not a valid Order");

    }

    public Order(OrderType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;

    }

    private static OrderType fetchTheCommandType(String commandString){
        if(commandString.charAt(1)=='e' && commandString.charAt(2)=='n'){
            return OrderType.ENTER;
        }else if (commandString.charAt(1)=='t' && commandString.charAt(2)=='r'){
            return OrderType.TRANSPORT;
        }else if (commandString.charAt(1)=='n' && commandString.charAt(2)=='o'){
            return OrderType.NORTH;
        }else if (commandString.charAt(1)=='s' && commandString.charAt(2)=='o'){
            return OrderType.SOUTH;
        }else if (commandString.charAt(1)=='e' && commandString.charAt(2)=='a'){
            return OrderType.EAST;
        }else if (commandString.charAt(1)=='w' && commandString.charAt(2)=='e'){
            return OrderType.WEST;
        }
        return null;
    }

    private static boolean isTheCommandCorrect(String commandString){

        if (fetchTheCommandType(commandString)!=null){
            if (fetchTheCommandType(commandString) == OrderType.TRANSPORT || fetchTheCommandType(commandString) == OrderType.ENTER){
                if (commandString.length()==41){
                    if (commandString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").replace("-","").equals("[,]")){
                        return true;
                    }
                }
            }else{
                if (commandString.length()==6){
                    if (commandString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").equals("[,]")){
                        return true;
                    }
                }
            }
        }else return false;

        return false;
    }

    public static Order fromString(String commandString ) {
        if (isTheCommandCorrect(commandString)){
            if (fetchTheCommandType(commandString) == OrderType.TRANSPORT || fetchTheCommandType(commandString) == OrderType.ENTER){
                return new Order(fetchTheCommandType(commandString),UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.length() - 1)));
            }else {
                return new Order(fetchTheCommandType(commandString),Integer.parseInt(commandString.replaceAll("[^0-9]", "")));
            }
        }else throw new RuntimeException("Invalid String");
    }

    public OrderType getOrderType() { return commandType; }
    public Integer getNumberOfSteps() { return numberOfSteps; }
    public UUID getGridId() { return gridId; }
}
