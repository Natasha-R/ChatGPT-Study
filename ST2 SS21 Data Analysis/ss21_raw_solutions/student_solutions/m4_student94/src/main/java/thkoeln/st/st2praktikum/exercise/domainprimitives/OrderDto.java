package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID spaceId;
}
