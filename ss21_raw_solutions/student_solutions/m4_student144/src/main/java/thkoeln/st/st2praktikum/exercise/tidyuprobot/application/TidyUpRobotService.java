package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.*;


import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    private TidyUpRobotRepository tidyUpRobotRepository;
    private CommandRepository commandRepository;
    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, CommandRepository commandRepository)
    {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.commandRepository = commandRepository;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        TidyUpRobotMap.addToMap(tidyUpRobot,tidyUpRobot.getTidyUpRobotId());
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getTidyUpRobotId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        Boolean worked = TidyUpRobotMap.getByUUID(tidyUpRobotId).understandCommandString(command);
        TidyUpRobotMap.getByUUID(tidyUpRobotId).addcommand(command);
        tidyUpRobotRepository.save(TidyUpRobotMap.getByUUID(tidyUpRobotId));


        return worked;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).getPoint();
    }

    public Iterable<TidyUpRobot> findAll(){
        return tidyUpRobotRepository.findAll();
    }

    public Iterable<Command> getAllCommands(UUID robotId){
        return TidyUpRobotMap.getByUUID(robotId).getCommands();
    }


    public TidyUpRobot findById(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow( () ->
                new RobotNotFoundException("Robot with ID:"+ tidyUpRobotId+" cant be found"));
        return tidyUpRobot;
    }

    public void deleteById(UUID id){

        tidyUpRobotRepository.delete(TidyUpRobotMap.getByUUID(id));
    }

    public void save(TidyUpRobot tidyUpRobot){
        tidyUpRobotRepository.save(tidyUpRobot);
    }

    public TidyUpRobot createNewRobotFromDto(TidyUpRobotDto tidyUpRobotDto){
        TidyUpRobot tidyUpRobot = tidyUpRobotDtoMapper.mapToEntity(tidyUpRobotDto);
        save(tidyUpRobot);
        return tidyUpRobot;
    }

    public void deleteAllCommands(UUID robotId){
        TidyUpRobot tidyUpRobot = TidyUpRobotMap.getByUUID(robotId);
        tidyUpRobot.getCommands().clear();
        tidyUpRobotRepository.save(tidyUpRobot);

    }

}
