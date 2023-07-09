package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;


@Component
public class TransportTechnologyServiceComponent {
    private TransportTechnologyService service;

    @Autowired
    public TransportTechnologyServiceComponent(TransportTechnologyService service) {
        this.service = service;
    }
}