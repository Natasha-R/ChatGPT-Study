package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.RobotMovement;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;


import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    @Autowired
    TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ConnectionRepository connectionRepository;


    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot();
        tidyUpRobot.setName(name);
        tidyUpRobot.setRoomID(UUID.randomUUID());
        tidyUpRobotRepository.save(tidyUpRobot);

        return tidyUpRobot.getTidyUpRobotID();

    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        RobotMovement robotMovement = new RobotMovement();
        return robotMovement.executeCommand( tidyUpRobotId , command , tidyUpRobotRepository , roomRepository , connectionRepository );

    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
    }
}
