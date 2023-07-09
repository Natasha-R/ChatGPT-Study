package thkoeln.st.st2praktikum.exercise.domainprimitives;
import lombok.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.internal.Pair;

import javax.persistence.Embeddable;
import java.util.NoSuchElementException;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    protected Order() {

    }

    public Order(OrderType orderType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new InvalidOrderException("The number of steps can't be negative");

        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    private static Pair<String, String> getStringCommandPair(String commandString) {
        String str = commandString
                .replace("[", "")
                .replace("]", "");
        String[] command = str.split(",");

        return Pair.of(command[0], command[1]);
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public static OrderType getOrderType(String orderTypeString) {
        switch (orderTypeString) {
            case "NORTH":
                return OrderType.NORTH;
            case "WEST":
                return OrderType.WEST;
            case "SOUTH":
                return OrderType.SOUTH;
            case "EAST":
                return OrderType.EAST;
            case "TRANSPORT":
                return OrderType.TRANSPORT;
            case "ENTER":
                return OrderType.ENTER;
            default:
                throw new NoSuchElementException();
        }
    }

    public static Order fromString(String orderString ) {
        Pair<String, String> commandString = getStringCommandPair(orderString);
        String type = commandString.getLeft();
        String content = commandString.getRight();

        switch (type) {
            case "no":
                return new Order(OrderType.NORTH, Integer.parseInt(content));
            case "ea":
                return new Order(OrderType.EAST, Integer.parseInt(content));
            case "we":
                return new Order(OrderType.WEST, Integer.parseInt(content));
            case "so":
                return new Order(OrderType.SOUTH, Integer.parseInt(content));
            case "tr":
                return new Order(OrderType.TRANSPORT, UUID.fromString(content));
            case "en":
                return new Order(OrderType.ENTER, UUID.fromString(content));

            default:
                throw new InvalidOrderException("The parameter \"orderString\" doesn't represent an order.");
        }
    }

    public static Order fromJSONObject(JSONObject jsonObject) throws JSONException {
        OrderType orderType = Order.getOrderType( (String) jsonObject.get("orderType") );

        if(!jsonObject.isNull("gridId")){
            UUID gridId = UUID.fromString( (String) jsonObject.get("gridId") );
            return new Order(orderType, gridId);
        }
        else{
            Integer numberOfSteps = (Integer) jsonObject.get("numberOfSteps");
            return new Order(orderType, numberOfSteps);
        }
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

}
