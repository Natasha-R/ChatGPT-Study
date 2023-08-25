package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;

@Component
public class TidyUpRobotComponent {
    private TidyUpRobotService service;

    public TidyUpRobotComponent (TidyUpRobotService service) {
        this.service = service;
    }
}
