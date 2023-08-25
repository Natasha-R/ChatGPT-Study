package thkoeln.st.st2praktikum.exercise.domainprimitives;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Order {



    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;
    private UUID id;




    public Order(OrderType orderType, Integer numberOfSteps) {


        if(numberOfSteps < 0) {
            throw new NegativeNumberException("Negative Schritte sind nicht erlaubt");
        }

        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;

    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;

    }







    public static Order fromString(String orderString ) {
         OrderType orderType = null;
        Integer numberOfSteps = null;
         UUID gridId = null;


        if (!orderString.substring(1, 3).equals("no")
                && !orderString.substring(1, 3).equals("so")
                && !orderString.substring(1, 3).equals("ea")
                && !orderString.substring(1, 3).equals("we")
                && !orderString.substring(1, 3).equals("tr")
                && !orderString.substring(1, 3).equals("en")
                || orderString.charAt(3) != ','
                || orderString.charAt(0) != '['
                || orderString.charAt(orderString.length() - 1) != ']'
        ) {
            throw new InvalidStringError("Dies ist kein erlaubter String");
        } else {

            if (orderString.substring(1, 3).equals("no")) {
                orderType = OrderType.NORTH;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, numberOfSteps);
                order.setId(UUID.randomUUID());
                return order;
            }
            if (orderString.substring(1, 3).equals("so")) {
                orderType = OrderType.SOUTH;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, numberOfSteps);
                order.setId(UUID.randomUUID());
                return order;
            }
            if (orderString.substring(1, 3).equals("we")) {
                orderType = OrderType.WEST;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, numberOfSteps);
                order.setId(UUID.randomUUID());
                return order;

            }
            if (orderString.substring(1, 3).equals("ea")) {
                orderType = OrderType.EAST;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, numberOfSteps);
                order.setId(UUID.randomUUID());
                return order;
            }
            if (orderString.substring(1, 3).equals("tr")) {
                orderType = OrderType.TRANSPORT;
                gridId = UUID.fromString(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, gridId);
                order.setId(UUID.randomUUID());
                return order;
            }
            if (orderString.substring(1, 3).equals("en")) {
                orderType = OrderType.ENTER;
                gridId = UUID.fromString(orderString.substring(4, orderString.length() - 1));
                Order order = new Order(orderType, gridId);
                order.setId(UUID.randomUUID());
                return order;
            }

        }
        return null;
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

    public void setId(UUID id) {
        this.id = id;
    }

    @Id
    public UUID getId() {
        return id;
    }

}
