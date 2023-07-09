package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CleaningDeviceComponent {
    private CleaningDeviceService service;

    @Autowired
    public CleaningDeviceComponent (CleaningDeviceService service) {
        this.service = service;
    }
}
