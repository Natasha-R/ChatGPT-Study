package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;

import java.util.UUID;

@Data
public class OrderDto {
    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;
}
