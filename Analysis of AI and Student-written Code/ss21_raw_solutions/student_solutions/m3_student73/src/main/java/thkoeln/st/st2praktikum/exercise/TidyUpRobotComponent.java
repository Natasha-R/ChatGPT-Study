package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Component;

@Component
public class TidyUpRobotComponent {
    private TidyUpRobotService service;

    public TidyUpRobotComponent (TidyUpRobotService service) {
        this.service = service;
    }
}
