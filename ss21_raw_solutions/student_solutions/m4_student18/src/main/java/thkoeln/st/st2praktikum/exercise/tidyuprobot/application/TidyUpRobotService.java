package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.ApplicationController;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.UUID;


@Service
public class TidyUpRobotService {

    ApplicationController applicationController;

    @Autowired
    public TidyUpRobotService(ApplicationController applicationController){
        this.applicationController = applicationController;
    }
    
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        applicationController.getTidyUpRobotRepository().save(tidyUpRobot);
        return tidyUpRobot.getId();
    }


    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        try {
            applicationController.execute(tidyUpRobotId, command);
            applicationController.getTidyUpRobotRepository().findById(tidyUpRobotId).get().getCommands().add(command);
        }
        catch (Exception exception) {
            System.out.println("Error while executing: "+command.toString()+"\n"+exception.getMessage()+"\n");
            return false;
        }
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return applicationController.getTidyUpRobotRepository().findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId){
        return applicationController.getTidyUpRobotRepository().findById(tidyUpRobotId).get().getRobotPosition();
    }

}
