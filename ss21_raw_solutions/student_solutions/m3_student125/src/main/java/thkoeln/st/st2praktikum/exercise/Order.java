package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidOrderException;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Order implements IParameterExtraction
{
    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Order(OrderType orderType, Integer numberOfSteps)
    {
        if (numberOfSteps < 0)
        {
            throw new InvalidOrderException("You provided a negative value for the number of steps the robot has to move!", new RuntimeException());
        }

        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
    }

    public Order(OrderType orderType, UUID gridId)
    {
        this.orderType = orderType;
        this.gridId = gridId;
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString)
    {
        String[] orderParameters = extractCommandParameters(orderString);

        String order = orderParameters[0];
        String parameter = orderParameters[1];

        switch(order)
        {
            case "no":
                this.orderType = OrderType.NORTH;
                break;
            case "so":
                this.orderType = OrderType.SOUTH;
                break;
            case "we":
                this.orderType = OrderType.WEST;
                break;
            case "ea":
                this.orderType = OrderType.EAST;
                break;
            case "tr":
                this.orderType = OrderType.TRANSPORT;
                break;
            case "en":
                this.orderType = OrderType.ENTER;
                break;
            default:
                throw new InvalidOrderException("You provided an invalid order string that is not supported by the command set of the robot!", new RuntimeException());
        }

        if (parameter.length() > 5)
        {
            this.gridId = UUID.fromString(parameter);
        }
        else
        {
            try
            {
                this.numberOfSteps = Integer.parseInt(parameter);
            }
            catch(NumberFormatException ex)
            {
                throw new InvalidOrderException("You provided an invalid order string that is not supported by the command set of the robot!", new RuntimeException());
            }
        }
    }

    public OrderType getOrderType()
    {
        return orderType;
    }

    public Integer getNumberOfSteps()
    {
        return numberOfSteps;
    }

    public UUID getGridId()
    {
        return gridId;
    }

    @Override
    public String[] extractCommandParameters(String commandString)
    {
        String[] result = new String[2];

        String command = commandString.split(",")[0].replace("[", "");
        String parameter = commandString.split(",")[1].replace("]", "");

        result[0] = command;
        result[1] = parameter;

        return result;
    }

    @Override
    public String[] extractCoordinates(String coordinateString)
    {
        return new String[0];
    }
}
