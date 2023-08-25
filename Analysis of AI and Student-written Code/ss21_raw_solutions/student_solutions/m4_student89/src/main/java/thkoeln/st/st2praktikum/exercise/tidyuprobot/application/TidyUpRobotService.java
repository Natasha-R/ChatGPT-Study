package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.UUID;


@Service
public class TidyUpRobotService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {

        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);

        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {


        if (task.getTaskType() == TaskType.ENTER) {
            if (spaceIsFree(task.getGridId())) {
                tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).en(task.getGridId());
                return true;
            } else {
                return false;

            }

        } else {

            TidyUpRobot t= tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId);
            if (t==null){
                return false;
            }
            else
                return t.performTask(task, roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
        }


    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){

        if (tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getNow_in_room() != null) {
            return tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getNow_in_room();
        } else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        if (tidyUpRobotId != null) {
            return tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getVector2D();
        } else
            throw new UnsupportedOperationException();
    }


    public boolean spaceIsFree(UUID roomid) {
        for (TidyUpRobot value : tidyUpRobotRepository.findAll()) {


            if (value.getPosx() == 0 && value.getPosy() == 0 && value.getNow_in_room().equals(roomid)) {

                return false;
            }
        }

        return true;


    }
    public Iterable<TidyUpRobot> finAll(){
        return tidyUpRobotRepository.findAll();
    }
    public TidyUpRobot updateRobots(TidyUpRobot tidyUpRobot){
        return tidyUpRobotRepository.save(tidyUpRobot);
    }
    public TidyUpRobot getTidyUpRobotByID(UUID id){

       return tidyUpRobotRepository.getTidyUpRobotByid(id);
    }
    public Room getRoom(UUID id){
        return roomRepository.getRoomByid(id);
    }
    public void removeTidyUpRobot(UUID id){
        tidyUpRobotRepository.removeTidyUpRobotByid(id);
    }
}
