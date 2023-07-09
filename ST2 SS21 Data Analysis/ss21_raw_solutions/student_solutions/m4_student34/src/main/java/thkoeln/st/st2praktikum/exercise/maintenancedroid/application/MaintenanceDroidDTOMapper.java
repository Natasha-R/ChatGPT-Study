package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

@Component
public class MaintenanceDroidDTOMapper {

    public MaintenanceDroidDTO mapToDTO(MaintenanceDroid maintenanceDroid) {
        return new MaintenanceDroidDTO(maintenanceDroid.getId(), maintenanceDroid.getName());
    }

    public MaintenanceDroid mapToEntity(MaintenanceDroidDTO maintenanceDroidDTO) {
        return null;
    }
}
