package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDroidDto {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private UUID spaceShipDeckId;

    @Getter
    @Embedded
    private Vector2D vector2D;

    @Getter
    @Embedded
    private List<Task> tasks;
}
