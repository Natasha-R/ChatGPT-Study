package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepo;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepo;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepo;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class TidyUpRobotService {

    private final TidyUpRobotRepo tidyUpRobotRepo;
    private final RoomService roomService;
    private final RoomRepo roomRepo;
    private final ConnectionRepo connectionRepo;
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    @Transactional
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepo.save(tidyUpRobot);
        System.out.println("added "+tidyUpRobot.getName());
        return tidyUpRobot.getId();
    }

    public TidyUpRobot findTidyUpRobot(UUID tidyUpRobotId){
        return tidyUpRobotRepo.findById(tidyUpRobotId).orElseThrow();
    }


    public Boolean checkSameCoordinate(Point firstCoordinate,Point secondCoordinate){
        return firstCoordinate.equals(secondCoordinate);
    }
    public String[] getCommandAsArray(String commandString){
        return commandString.replace("[","").replace("]","").split(",");
    }
    public Boolean enterTheRoom(TidyUpRobot tidyUpRobot,String roomId){
        Room foundRoom = roomService.findRoomWithStringAsId(roomId);
        if(foundRoom==null) return false;

        List<TidyUpRobot> tidyUpRobotList = tidyUpRobotRepo.findAll();
        for(TidyUpRobot someRobot: tidyUpRobotList){
            if(someRobot.getLocY() != null && someRobot.getLocX() != null) {
                if(someRobot.getLocX() == 0 && someRobot.getLocY() == 0) return false;
            }
        }

        tidyUpRobot.spawn(foundRoom);
        return true;
    }

    public List<Point> getPointsFromObstacle(Obstacle obstacle){
        List<Point> pointList = new ArrayList<>();
        if(obstacle.getStart().getX().equals(obstacle.getEnd().getX())){
            for(int i = obstacle.getStart().getY(); i<=obstacle.getEnd().getY(); i++){
                pointList.add(new Point(obstacle.getStart().getX(),i));
            }
        }
        else{
            for(int i = obstacle.getStart().getX(); i<=obstacle.getEnd().getX(); i++){
                pointList.add(new Point(i,obstacle.getStart().getY()));
            }
        }
        return pointList;
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = findTidyUpRobot(tidyUpRobotId);
        tidyUpRobot.addTaskToTaskList(task);

        switch (task.getTaskType()){
            case ENTER:
                System.out.println("ENTERED");
                return enterTheRoom(tidyUpRobot,task.getGridId().toString());
            case TRANSPORT:
                System.out.println(tidyUpRobot.getRoom().getId());
                System.out.println(task.getGridId());
                System.out.println(tidyUpRobot.getCoordinates());
                boolean sameCoordinate = false;
                List<Connection> connectionList = connectionRepo.findAll();
                List<Room> roomList = roomRepo.findAll();
                for (Connection connector : connectionList) {
                    if (checkSameCoordinate(getTidyUpRobotPoint(tidyUpRobotId), connector.getSourceCoordinate())) {
                        System.out.println("IF1");
                        if (connector.getDestinationRoomId().equals(task.getGridId())) {
                            System.out.println("CONNECTOR SAME");

                            for (Room room : roomList) {
                                System.out.println("ROOM");
                                if (room.getId() == connector.getDestinationRoomId()) {
                                    System.out.println("IF2");
                                    tidyUpRobot.transport(connector, room);
                                }
                            }
                        }
                        sameCoordinate = true;
                    }
                }

                if (!sameCoordinate) {
                    return false;
                }
                break;
            case EAST:
                System.out.println("EAST STEPS "+task.getNumberOfSteps());
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getEastEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(1);
                    }
                }
                break;
            case WEST:
                System.out.println("WEST STEPS "+task.getNumberOfSteps());
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getWestEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveX(-1);
                    }
                }
                System.out.println(tidyUpRobot.getCoordinates());
                break;
            case SOUTH:
                System.out.println("SOUTH STEPS "+task.getNumberOfSteps());
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getSouthEdge();
                    searchObstacle:
                    {
                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            if(obstacles.contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveY(-1);
                    }
                }
                break;
            case NORTH:
                System.out.println("NORD STEPS "+task.getNumberOfSteps());
                for (int i = 0; i < task.getNumberOfSteps(); i++) {
                    List<Point> edge = tidyUpRobot.getNordEdge();
                    searchObstacle:
                    {

                        for (Obstacle obstacle : tidyUpRobot.getRoom().getObstacleList()) {
                            //System.out.println("OBSTACLES "+obstacle.getStart().toString()+","+obstacle.getEnd().toString());
                            List<Point> obstacles = getPointsFromObstacle(obstacle);
                            //System.out.println("EDGES "+ edge.get(0)+","+edge.get(1));

                            if(getPointsFromObstacle(obstacle).contains(edge.get(0))&&obstacles.contains(edge.get(1))){
                                break searchObstacle;
                            }
                        }
                        tidyUpRobot.moveY(1);
                    }
                }
                break;

            default:
                return false;
        }
        return false;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepo.findTidyUpRobotById(tidyUpRobotId).getRoom().getId();

    }

    public List<TidyUpRobot> getAllTidyUpRobots(){
        return tidyUpRobotRepo.findAll();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepo.findTidyUpRobotById(tidyUpRobotId).getPoint();

    }
}
