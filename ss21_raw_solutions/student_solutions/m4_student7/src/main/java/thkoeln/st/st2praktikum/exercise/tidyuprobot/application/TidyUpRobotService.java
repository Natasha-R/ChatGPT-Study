package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import java.util.*;
import java.util.stream.Stream;


@Service
public class TidyUpRobotService {

    @Setter
    private RoomService roomService;
    @Setter
    private TransportCategoryService transportCategoryService;

    private List<TidyUpRobot> tidyUpRobots = new ArrayList<TidyUpRobot>();

    private TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository){
        this.tidyUpRobotRepository = tidyUpRobotRepository;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobots.add(tidyUpRobot);
        persistNewData();
        return tidyUpRobot.getId();
    }

    public TidyUpRobot addTidyUpRobotFromDto(TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getName());
        tidyUpRobots.add(tidyUpRobot);
        persistNewData();
        return tidyUpRobot;
    }

    public void deleteOneTidyUpRobot(UUID id){
        tidyUpRobots.remove(getRobotByRobotId(id));
        persistNewData();
    }

    public void patchTidyUpRobot(UUID id, String name){
        getRobotByRobotId(id).setName(name);
        persistNewData();
    }


    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = getRobotByRobotId(tidyUpRobotId);
        tidyUpRobot.addTask(task);

        switch (task.getTaskType()){
            case TRANSPORT:
                Boolean transportResult = transportCategoryService.transportRobot(tidyUpRobot, task);
                persistNewData();
                return transportResult;
            case ENTER:
                return placeRobot(tidyUpRobot, task);
            default:
                return moveRobot(tidyUpRobot, task);
        }
    }



    public Boolean destinationNotOccupied(Room destinationRoom, Vector2D coordinates, TidyUpRobot tidyUpRobot){
        return tidyUpRobots.stream().noneMatch(findTidyUpRobot ->
                findTidyUpRobot.getRoom() == destinationRoom
                        && findTidyUpRobot.getVector2D().equals(coordinates)
                        && findTidyUpRobot != tidyUpRobot);
    }

    private Boolean placeRobot(TidyUpRobot tidyUpRobot, Task task) {
        Room destinationRoom = roomService.getRoomByRoomId(task.getGridId());
        if(tidyUpRobot.getRoom() == null && destinationNotOccupied(destinationRoom, new Vector2D(0,0), tidyUpRobot)){
            tidyUpRobot.setRoom(destinationRoom);
            destinationRoom.addBlocker(tidyUpRobot);
            tidyUpRobot.move(new Vector2D(0,0));

            persistNewData();
            return true;
        }else
            return false;
    }

    private Boolean moveRobot(TidyUpRobot tidyUpRobot, Task task) {
        Vector2D coordinates = tidyUpRobot.getVector2D();
        Integer [] movement;
        int steps = task.getNumberOfSteps();
        switch (task.getTaskType()){    //direction
            case NORTH:
                movement = new Integer[]{0, 1};
                break;
            case EAST:
                movement = new Integer[]{1, 0};
                break;
            case SOUTH:
                movement = new Integer[]{0, -1};
                break;
            case WEST:
                movement = new Integer[]{-1, 0};
                break;
            default:
                return true;
        }
        for(int i = 0; i < steps; i++){
            try {
                coordinates = new Vector2D(coordinates.getX() + movement[0],coordinates.getY() + movement[1]);
                Room room = tidyUpRobot.getRoom();
                if(room.isCoordinateInBounds(coordinates) && room.isCoordinateEmpty(coordinates))
                    tidyUpRobot.move(coordinates);
                else
                    break;
            }catch (UnsupportedOperationException e){
            }
        }
        persistNewData();
        return true;
    }

    public void persistNewData(){
        tidyUpRobotRepository.deleteAll();


        tidyUpRobots.forEach(tidyUpRobot -> tidyUpRobotRepository.save(tidyUpRobot));
    }

    public Iterable<TidyUpRobot> getAll(){
        return tidyUpRobots;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return getRobotByRobotId(tidyUpRobotId).getRoom().getId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        return getRobotByRobotId(tidyUpRobotId).getVector2D();
    }

    public TidyUpRobot getRobotByRobotId(UUID robotId){
        return tidyUpRobots.stream().filter(findTidyUpRobot -> findTidyUpRobot.getId().equals(robotId)).findAny().orElse(null);
    }

}
