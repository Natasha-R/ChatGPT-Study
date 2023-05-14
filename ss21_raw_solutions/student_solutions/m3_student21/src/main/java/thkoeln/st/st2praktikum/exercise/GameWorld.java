package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameWorld {

    private TidyUpRobotService tidyUpRobotService;

    @Autowired
    public GameWorld(TidyUpRobotService service){
        this.tidyUpRobotService = service;
    }
}
