package thkoeln.st.st2praktikum.exercise;

import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Embeddable;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;


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

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString) {
  /*      assertThrows(RuntimeException.class, () -> new Order("[soo,4]"));
        assertThrows(RuntimeException.class, () -> new Order("[4, no]"));
        assertThrows(RuntimeException.class, () -> new Order("[[soo,4]]"));
        assertThrows(RuntimeException.class, () -> new Order("(soo,4)"));

   */
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
            }
            if (orderString.substring(1, 3).equals("so")) {
                orderType = OrderType.SOUTH;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
            }
            if (orderString.substring(1, 3).equals("we")) {
                orderType = OrderType.WEST;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));

            }
            if (orderString.substring(1, 3).equals("ea")) {
                orderType = OrderType.EAST;
                numberOfSteps = Integer.parseInt(orderString.substring(4, orderString.length() - 1));
            }
            if (orderString.substring(1, 3).equals("tr")) {
                orderType = OrderType.TRANSPORT;
                gridId = UUID.fromString(orderString.substring(4, orderString.length() - 1));
            }
            if (orderString.substring(1, 3).equals("en")) {
                orderType = OrderType.ENTER;
                gridId = UUID.fromString(orderString.substring(4, orderString.length() - 1));
            }

        }

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
