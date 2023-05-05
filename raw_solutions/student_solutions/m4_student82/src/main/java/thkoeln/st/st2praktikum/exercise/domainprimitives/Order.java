package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Exception.NoFieldException;
import thkoeln.st.st2praktikum.exercise.Exception.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.Exception.WrongOrderException;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.DeckRepository;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Getter
@AllArgsConstructor
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Order() {
        
    }

    public Order(OrderType orderType, Integer numberOfSteps) {
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
    
    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public static Order fromString(String orderString ) {
        String newCommandRaw = orderString.substring(1, orderString.length() -1);
        String[] commandParts = newCommandRaw.split(",");
        OrderType order = null;
        order = stringToOrderType(commandParts[0]);
        switch (order){
            case NORTH:
            case EAST:
            case WEST:
            case SOUTH:
                if(Integer.parseInt(commandParts[1]) > 0 && Integer.parseInt(commandParts[1] ) >= 0){

                    return new Order(order, Integer.parseInt(commandParts[1]));

                }else{
                    throw new WrongOrderException(orderString);
                }
            case ENTER:
            case TRANSPORT:
                return new Order(order, UUID.fromString(commandParts[1]));

        }

        throw new WrongOrderException(orderString);
    }

    static OrderType stringToOrderType(String orderString) throws WrongOrderException{

        switch (orderString){
            case "no":
                return OrderType.NORTH;

            case "so":
                return OrderType.SOUTH;

            case"ea":
                return OrderType.EAST;

            case"we":
                return  OrderType.WEST;

            case"tr":
                return OrderType.TRANSPORT;

            case"en":
                return OrderType.ENTER;
            default:
                throw new WrongOrderException(orderString);
        }


    }

    public static boolean orderWithUUID(Order order, MaintenanceDroid maintenanceDroid, DeckRepository decks) {
        switch (order.getOrderType()){
            case TRANSPORT:
                try {
                    return maintenanceDroid.travel(decks);
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            case ENTER:

                try {
                    return maintenanceDroid.spawn(decks, order.getGridId());
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }


    }

    public static boolean orderWithPower(Order order, MaintenanceDroid maintenanceDroid, DeckRepository decks){
        for(int i = 0; i < order.getNumberOfSteps(); i++) {
            try {
                if (maintenanceDroid.canGoForward(order.getOrderType(), decks) == Decision.CAN_GO_FORWARD) {
                    try {
                        maintenanceDroid.move(order.getOrderType(), decks);
                    } catch (NotSpawnedYetException e) {
                        System.out.println(e.toString());
                    }

                }
            } catch (NotSpawnedYetException e) {
                System.out.println(e.toString());
            }
        }
        return true;
    }
    
}
