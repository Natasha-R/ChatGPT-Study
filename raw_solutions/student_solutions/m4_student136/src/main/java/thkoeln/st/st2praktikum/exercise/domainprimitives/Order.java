package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.UUID;

@Embeddable
@Getter
@EqualsAndHashCode
public class Order
{

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Order()
    {
        
    }

    public Order(OrderType orderType, Integer numberOfSteps)
    {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;

        // if the number of steps is negative
        if (numberOfSteps < 0)
        {
            throw new OrderException();
        }
    }

    public Order(OrderType orderType, UUID gridId)
    {
        this.orderType = orderType;
        this.numberOfSteps = 0;
        this.gridId = gridId;

        // if the number of steps is negative
        if (numberOfSteps < 0)
        {
            throw new OrderException();
        }
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString)
    {
        String[] orders = orderString.substring(1, orderString.length()-1).split(",");

        // if the brackets are correct
        if (orderString.charAt(0) != '[' || orderString.charAt(orderString.length()-1) != ']')
        {
            throw new OrderException();
        }
        // if the amount of commas is correct
        if (orders.length != 2)
        {
            throw new OrderException();
        }

        boolean movementOrder = true;

        if(orders[0].equals("no"))
        {
            orderType = OrderType.NORTH;
        }
        else if(orders[0].equals("we"))
        {
            orderType = OrderType.WEST;
        }
        else if(orders[0].equals("so"))
        {
            orderType = OrderType.SOUTH;
        }
        else if(orders[0].equals("ea"))
        {
            orderType = OrderType.EAST;
        }
        else if(orders[0].equals("tr"))
        {
            orderType = OrderType.TRANSPORT;
            movementOrder = false;
        }
        else if(orders[0].equals("en"))
        {
            orderType = OrderType.ENTER;
            movementOrder = false;
        }
        else
        {
            throw new OrderException();
        }

        // whether the numbers / the UUID were/was correct
        try
        {
            if(movementOrder)
            {
                numberOfSteps = Integer.parseInt(orders[1]);
            }
            else
            {
                numberOfSteps = 0;
                gridId = UUID.fromString(orders[1]);
            }
        }
        catch(Exception e)
        {
            throw new OrderException();
        }

        // if the number of steps is negative
        if (numberOfSteps < 0)
        {
            throw new OrderException();
        }
    }

    @Transient
    public OrderType getOrderType()
    {
        return orderType;
    }

    @Transient
    public Integer getNumberOfSteps()
    {
        return numberOfSteps;
    }

    @Transient
    public UUID getGridId()
    {
        return gridId;
    }

    public static Order fromString(String orderString)
    {
        return new Order(orderString);
    }
    
}
