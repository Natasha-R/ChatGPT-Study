package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiningMachineDto {
    private UUID miningMachineId;
    private String name;
    private Coordinate coordinate;
    private UUID fieldId;
    private List<Order> orders;
}
