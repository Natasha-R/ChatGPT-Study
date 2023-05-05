package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobot extends AbstractEntity implements executeCommand, move,placeRobot {

    public TidyUpRobot(String name, UUID roboterUUID) {
        this.name = name;
        this.roboterUUID = roboterUUID;

    }

    private UUID roomId;
    @Id
    private UUID roboterUUID;


    public UUID getId() {
        return roboterUUID;
    }

    public void setId(UUID id) {
        this.roboterUUID = id;
    }

    private String name;
    private boolean canDoAction = true;


    @Embedded
    private Coordinate coordinates = Coordinate.fromString("(0,0)");

    @Getter
    @ElementCollection
    private List<Task> taskList = new ArrayList<Task>();



    @Override
    public Coordinate executeCommand(TaskType moveCommand, Integer steps, UUID UUID, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots, RoomRepository roomRepository, TransportCategoryRepository transportCategoryRepository) {
        switch (moveCommand) {
            case ENTER:
                coordinates = placeRobot(UUID, tidyUpRobotList, tidyUpRobotRepository, tidyRobot, coordinatesForRobots);
                break;
            case TRANSPORT:
                coordinates = moveConnection(tidyRobot, tidyUpRobotList, tidyUpRobotRepository, roomWhereRobotIs, transportCategoryRepository);
                break;
            case NORTH:
                coordinates = moveNorth(steps, tidyUpRobotList, tidyUpRobotRepository, roomWhereRobotIs, roomRepository);
                break;
            case SOUTH:
                coordinates = moveSouth(steps, tidyUpRobotList, tidyUpRobotRepository, roomWhereRobotIs, roomRepository);
                break;
            case EAST:
                coordinates = moveEast(steps, tidyUpRobotList, tidyUpRobotRepository, roomWhereRobotIs, roomRepository);
                break;
            case WEST:
                coordinates = moveWest(steps, tidyUpRobotList, tidyUpRobotRepository, roomWhereRobotIs, roomRepository);
                break;
        }
        return coordinates;
    }

    @Override
    public Coordinate moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository) {
        boolean theBreak = false;
        Iterable<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
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
                        coordinates = Coordinate.fromString("(" + coordinates.getX() + "," + (coordinates.getY() + 1) + ")");

                    }
                }
            }
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository) {
        boolean theBreak = false;
        Iterable<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
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
                        coordinates = Coordinate.fromString("(" + coordinates.getX() + "," + (coordinates.getY() - 1) + ")");
                    }

                }
            }
        }
        if (theBreak) {
            coordinates = Coordinate.fromString("(" + coordinates.getX() + "," + (coordinates.getY() - 1) + ")");
        }
        roomId = roomWhereRobotIs;
        return coordinates;

    }


    @Override
    public Coordinate moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository) {
        boolean theBreak = false;
        Iterable<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
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
                        coordinates = Coordinate.fromString("(" + (coordinates.getX() + 1) + "," + coordinates.getY() + ")");
                    }
                }

            }
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository) {
        boolean theBreak = false;
        Iterable<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
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
                        coordinates = Coordinate.fromString("(" + (coordinates.getX() - 1) + "," + coordinates.getY() + ")");
                    }
                }

            }
        }
        if (theBreak) {
            coordinates = Coordinate.fromString("(" + (coordinates.getX() - 1) + "," + coordinates.getY() + ")");
        }
        roomId = roomWhereRobotIs;
        return coordinates;
    }

    @Override
    public Coordinate moveConnection(UUID tidyRobot, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, TransportCategoryRepository transportCategoryRepository) {
        Iterable<TransportCategory> transportCategoriesList = transportCategoryRepository.findAll();
        Boolean breaker = true;
        for (TransportCategory transportCategory : transportCategoriesList ){
            for (Connection connection : transportCategory.getListConnection()) {
                canDoAction = coordinates.equals(connection.getSourceCoordinate());
                if (canDoAction && breaker) {
                    coordinates = connection.getDestinationCoordinate();
                    roomId = connection.getDestinationRoomId();
                    breaker = false;
                }

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
        for (TidyUpRobot robot : tidyUpRoboterList.getTidyUpRobotsList()) {
            if (robot.getId() == tidyRobot) {
                tidyUpRobotRepository.save(robot);
            }
        }

        return coordinates;
    }


    public String getName() {
        return name;
    }


    public UUID getRoomId() {
        return roomId;
    }


    public boolean isCanDoAction() {
        return canDoAction;
    }

    public Coordinate getCoordinate() {
        return coordinates;
    }

    public void setCoordinate(Coordinate coordinates) {
        this.coordinates = coordinates;
    }



}
