package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceService;



@Component
public class SpaceServiceComponent {
    private SpaceService service;

    @Autowired
    public SpaceServiceComponent(SpaceService service) {
            this.service = service;
        }
}
