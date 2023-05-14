package thkoeln.st.st2praktikum.exercise.domainprimitives;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;
}