package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exception.InvalidNumberOfStepsException;
import thkoeln.st.st2praktikum.exercise.exception.InvalidOrderException;

import java.util.UUID;

@Getter
@Setter
public class Order
{
    private OrderType orderType; // bad name -> CommandType
    private Integer numberOfSteps;
    private UUID gridId; // bad name -> RoomID

    public Order()
    {
        this(OrderType.ENTER, 0, null);
    }

    public Order(OrderType orderType, Integer numberOfSteps)
    {
        this(orderType, numberOfSteps,null);
    }

    public Order(OrderType orderType, UUID gridId)
    {
        this(orderType, 0,gridId);
    }

    public Order(OrderType orderType, Integer numberOfSteps, UUID gridId)
    {
        setType(orderType);
        setNumberOfSteps(numberOfSteps);
        setGridID(gridId);
    }

    /**
     * @param orderString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Order(String orderString)
    {
        parseCommandString(orderString);
    }

    public static Order fromString(String orderString )
    {
        return new Order(orderString);
    }

    private void parseCommandString(String string)
    {
        if(!(string.length() >= 6)) // validLengh
        {
            throw new InvalidOrderException("Command is far to small");
        }

        if(!(string.charAt(0) == '[' && string.charAt(string.length()-1) == ']')) // has Brackets
        {
            throw new InvalidOrderException("Command has invalid Brackets -> []");
        }

        final int comma = string.indexOf(',');
        final String valueString = string.substring(comma+1, string.length()-1);

        parseCommandType(string.substring(1, comma));

        switch (getType())
        {
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                setNumberOfSteps(Integer.parseInt(valueString));
                break;

            case TRANSPORT:
            case ENTER:
                setGridID(UUID.fromString(valueString));
                break;
        }
    }

    private void parseCommandType(String string)
    {
        if(string.length() != 2)
        {
            throw new InvalidOrderException(string + " has to be 2 characters long.");
        }

        switch (string)
        {
            case "we":
                setType(OrderType.WEST);
                break;

            case "no":
                setType(OrderType.NORTH);
                break;

            case "ea":
                setType(OrderType.EAST);
                break;

            case "so":
                setType(OrderType.SOUTH);
                break;

            case "tr":
                setType(OrderType.TRANSPORT);
                break;

            case "en":
                setType(OrderType.ENTER);
                break;

            default:
                throw new InvalidOrderException(string + " is not supported as an command.");
        }
    }

    public OrderType getOrderType()
    {
        return orderType;
    }

    private void setNumberOfSteps(int steps)
    {
        if(steps < 0)
        {
            throw new InvalidNumberOfStepsException("Negative steps are not allowed.");
        }

        numberOfSteps = steps;
    }

    @Override
    public String toString()
    {
        String tag = "ERROR";
        String value = "ERROR";

        switch (getType())
        {
            case NORTH:
                tag = "no";
                value = getNumberOfSteps().toString();
                break;

            case WEST:
                tag = "we";
                value = getNumberOfSteps().toString();
                break;

            case SOUTH:
                tag = "so";
                value = getNumberOfSteps().toString();
                break;

            case EAST:
                tag = "ea";
                value = getNumberOfSteps().toString();
                break;

            case TRANSPORT:
                tag = "tr";
                value = getGridId().toString();
                break;

            case ENTER:
                tag = "en";
                value = getGridId().toString();
                break;
        }

        return "[" + tag + "," + value + "]";
    }

    public Integer getNumberOfSteps()
    {
        return numberOfSteps;
    }

    public UUID getGridId()
    {
        return gridId;
    }

    private void setType(OrderType type)
    {
        orderType = type;
    }

    public OrderType getType()
    {
        return orderType;
    }

    private void setGridID(UUID uuid)
    {
        gridId = uuid;
    }

    public UUID getGridID()
    {
        return gridId;
    }
    
}
