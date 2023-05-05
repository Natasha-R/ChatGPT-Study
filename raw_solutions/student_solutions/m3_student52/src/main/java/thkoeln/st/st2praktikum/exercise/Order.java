package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Embeddable @EqualsAndHashCode
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    private static final HashMap<String, OrderType> mOrders = new HashMap<>();
    private static final HashMap<String, OrderType> eOrders = new HashMap<>();

    static {
        mOrders.put("no",OrderType.NORTH);
        mOrders.put("ea",OrderType.EAST);
        mOrders.put("so",OrderType.SOUTH);
        mOrders.put("we",OrderType.WEST);

        eOrders.put("en", OrderType.ENTER);
        eOrders.put("tr", OrderType.TRANSPORT);
    }



    public Order(String cmd){
        var s = cmd.replace("[","").replace("]","").split(",");
        if(s.length != 2) throw new InvalidCommandException("Invalid command length");
        var c = mOrders.get(s[0]);
        if (c!= null){
            this.orderType = c;
            this.numberOfSteps = Integer.parseInt(s[1]);
        }else {
            c = eOrders.get(s[0]);
            if (c == null) throw new InvalidCommandException("Order type does not exist: "+ cmd);
            this.orderType = c;
            this.gridId= UUID.fromString(s[1]);
        }
    }
    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new InvalidCommandException("Negative steps are not supported");
        }
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    @Override
    public String toString() {
        if (numberOfSteps != null)
            return "[" + orderType.toString() + "," + numberOfSteps.toString() + "]";
        else
            return "[" + orderType.toString() + "," + gridId.toString() + "]";
    }
}
