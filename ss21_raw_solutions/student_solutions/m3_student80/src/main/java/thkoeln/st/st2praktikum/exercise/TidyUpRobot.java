package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;


import javax.persistence.*;
import java.util.UUID;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobot extends AbstractEntity implements executeCommand, placeRobot, move {

    private UUID roomId;
    private UUID roboterUUID;
    @Id
    public UUID getRoboterUUID() {
        return roboterUUID;
    }

    public void setId(UUID id) {
        this.roboterUUID = id;
    }
    private String name;
    private boolean canDoAction = true;





    @Embedded
    private Coordinate coordinates = new Coordinate("(0,0)");


    public TidyUpRobot(String name, UUID roboterUUID) {
        this.name = name;
        this.roboterUUID = roboterUUID;

    }


    @Override
    public Coordinate executeCommand(TaskType moveCommand, Integer steps, UUID UUID, TidyUpRoboterList tidyUpRobotList, RoomList roomList, ConnectionList connectionList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
        switch (moveCommand) {
            case ENTER:
                coordinates = placeRobot(UUID, tidyUpRobotList, tidyUpRobotRepository, tidyRobot, coordinatesForRobots);
                break;
            case TRANSPORT:
                coordinates = moveConnection(tidyRobot, tidyUpRobotList, connectionList, tidyUpRobotRepository, roomWhereRobotIs);
                break;
            case NORTH:
                coordinates = moveNorth(steps, tidyUpRobotList, roomList, tidyUpRobotRepository, roomWhereRobotIs);
                break;
            case SOUTH:
                coordinates = moveSouth(steps, tidyUpRobotList, roomList, tidyUpRobotRepository, roomWhereRobotIs);
                break;
            case EAST:
                coordinates = moveEast(steps, tidyUpRobotList, roomList, tidyUpRobotRepository, roomWhereRobotIs);
                break;
            case WEST:
                coordinates = moveWest(steps, tidyUpRobotList, roomList, tidyUpRobotRepository, roomWhereRobotIs);
                break;
        }
        return coordinates;
    }

    @Override
    public Coordinate moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs) {
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < steps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates.getY() + 1 == barrier.getStart().getY()) {
                            if (coordinates.getX() >= barrier.getStart().getX() && coordinates.getX() < barrier.getEnd().getX()) {
                                theBreak = true;
                            }
                        }
                    }

                    if (!theBreak) {
                        coordinates = new Coordinate("(" + coordinates.getX() + "," + (coordinates.getY()+1) + ")");

                    }
                }
            }
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs) {
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < steps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates.getY() - 1 == barrier.getStart().getY()) {
                            if (coordinates.getX() >= barrier.getStart().getX() && coordinates.getX() < barrier.getEnd().getX()) {
                                theBreak = true;

                            }

                        }
                    }
                    if (!theBreak) {
                        coordinates = new Coordinate("("+ coordinates.getX() + ","+ (coordinates.getY()-1)+ ")" );
                    }

                }
            }
        }
        if (theBreak) {
            coordinates = new Coordinate("("+ coordinates.getX() + ","+ (coordinates.getY()-1)+ ")" );
        }
        roomId = roomWhereRobotIs;
        return coordinates;

    }


    @Override
    public Coordinate moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs) {
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < steps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates.getX() + 1 == barrier.getStart().getX()) {
                            if (coordinates.getY() >= barrier.getStart().getY() && coordinates.getX() < barrier.getEnd().getY()) {

                                theBreak = true;

                            }
                        }

                    }
                    if (!theBreak) {
                        coordinates = new Coordinate("("+ (coordinates.getX()+1) + ","+ coordinates.getY() + ")" );
                    }
                }

            }
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs) {
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < steps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates.getX() - 1 == barrier.getStart().getX()) {
                            if (coordinates.getY() >= barrier.getStart().getY() && coordinates.getX() < barrier.getEnd().getY()) {

                                theBreak = true;

                            }
                        }

                    }
                    if (!theBreak) {
                        coordinates = new Coordinate("("+ (coordinates.getX()-1) + ","+ coordinates.getY() + ")" );
                    }
                }

            }
        }
        if (theBreak) {
            coordinates = new Coordinate("("+ (coordinates.getX()-1) + ","+ coordinates.getY()+ ")" );
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveConnection(UUID tidyRobot, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs) {
        for (Connection connection : connectionList.getConnectionList()) {
            canDoAction = coordinates.equals(connection.getSourceCoordinate());
            if (canDoAction) {
                coordinates = connection.getDestinationCoordinate();
                roomId = connection.getDestinationRoomId();
                break;
            }
        }
        return coordinates;
    }


    @Override
    public Coordinate placeRobot(UUID roomUUID, TidyUpRoboterList tidyUpRoboterList, TidyUpRobotRepository tidyUpRobotRepository, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
        boolean breaker = false;
        int[] tempcoordinates = coordinatesForRobots.getCoordinatesForRobots().
                get(tidyUpRoboterList.getTidyUpRobotsList().get(0).roboterUUID);

        for (TidyUpRobot tidyUpRobot : tidyUpRoboterList.getTidyUpRobotsList()) {
            if (tempcoordinates != null) {
                if (tempcoordinates[0] == tidyUpRobot.coordinates.getX()) {
                    breaker = true;
                    canDoAction = false;
                }
            }
        }
        if (!breaker) {
            canDoAction = true;
            int[] temptempcoordinates = new int[]{0};
            coordinatesForRobots.getCoordinatesForRobots().put(tidyUpRoboterList.getTidyUpRobotsList().get(0).roboterUUID, temptempcoordinates);
        }
        roomId = roomUUID;
        for(TidyUpRobot robot : tidyUpRoboterList.getTidyUpRobotsList()){
            if(robot.getRoboterUUID()== tidyRobot){
                tidyUpRobotRepository.save(robot);
            }
        }

        return coordinates;
    }



    public String getName() {
        return name;
    }


    public UUID getRoomId(){
        return roomId;
    }



    public boolean isCanDoAction() {
        return canDoAction;
    }

    public Coordinate getCoordinate(){
        return coordinates;
    }

    public void setCoordinate (Coordinate coordinates){
        this.coordinates = coordinates;
    }

}
