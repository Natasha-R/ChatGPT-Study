package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import java.util.UUID;

@AllArgsConstructor
public class MaintenanceDroidDTO {
    @Getter
    private UUID id;
    @Getter
    private String name;
}
