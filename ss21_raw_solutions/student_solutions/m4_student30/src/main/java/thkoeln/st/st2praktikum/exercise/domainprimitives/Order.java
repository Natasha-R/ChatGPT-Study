package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.Pattern;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;


    protected Order() {

    }

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        if (numberOfSteps < 0) {
            throw new RuntimeException("Negative Schritte nicht zulässig");
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

    public static Order fromString(String taskString ) {

        if (taskString == null) {
            throw new UnsupportedOperationException();
        }

        String[] parts = taskString.replaceAll("\\[|]", "").split(Pattern.quote(","));

        if (parts.length < 2) {
            throw new RuntimeException("Falscher Eingabestring");
        }


        if (validCommandAndValueCHECK(parts[0],parts[1])) {
            return correctInit(parts);
        }
        throw new UnsupportedOperationException();
    }
    private static boolean validCommandAndValueCHECK(String command, String value)
    {
        String uuidCheck[] = value.split("-");
        if (uuidCheck.length != 5 && (command == "tr" || command == "en")) {
        throw new IllegalArgumentException("gridId erwartet aber nicht bekommen");
    }
        if (uuidCheck.length == 5 && (command == "no" || command == "ea" || command == "so"||  command == "we")) {
        throw new IllegalArgumentException("Steps erwartet aber UUID bekommen");
    }
        return true;

    }
    private static Order correctInit(String[] str)
    {
        switch (str[0]) {
            case "tr":
                return new Order(OrderType.TRANSPORT,UUID.fromString(str[1])) ;

            case "en":
                return new Order(OrderType.ENTER,UUID.fromString(str[1])) ;

            case "no":
                return new Order(OrderType.NORTH,Integer.parseInt(str[1])) ;

            case "ea":
                return new Order(OrderType.EAST,Integer.parseInt(str[1])) ;

            case "so":
                return new Order(OrderType.SOUTH,Integer.parseInt(str[1])) ;

            case "we":
                return new Order(OrderType.WEST,Integer.parseInt(str[1])) ;
            default:
                throw new RuntimeException("Falscher taskType");
        }


    }

}