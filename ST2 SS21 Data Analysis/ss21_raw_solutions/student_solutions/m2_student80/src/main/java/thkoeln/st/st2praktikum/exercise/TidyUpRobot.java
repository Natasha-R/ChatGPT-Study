package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class TidyUpRobot implements executeCommand, placeRobot, move {
    private UUID roboterUUID;
    private String name;
    private Coordinate coordinates = new Coordinate("(0,0)");
    private boolean canDoAction = true;

    public TidyUpRobot(String name, UUID roboterUUID) {
        this.name = name;
        this.roboterUUID = roboterUUID;

    }


    @Override
    public RoomRobotorHashMap executeCommand(TaskType moveCommand, Integer steps, UUID UUID, TidyUpRoboterList tidyUpRobotList, RoomList roomList, ConnectionList connectionList, RoomRobotorHashMap roomRobotorHashMap, UUID roomWhereRobotIs, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
        switch (moveCommand) {
            case ENTER:
                roomRobotorHashMap = placeRobot(UUID, tidyUpRobotList, roomRobotorHashMap, tidyRobot, coordinatesForRobots);
                break;
            case TRANSPORT:
                roomRobotorHashMap = moveConnection(tidyRobot, tidyUpRobotList, connectionList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case NORTH:
                roomRobotorHashMap = moveNorth(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case SOUTH:
                roomRobotorHashMap = moveSouth(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case EAST:
                roomRobotorHashMap = moveEast(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case WEST:
                roomRobotorHashMap = moveWest(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
        }
        return roomRobotorHashMap;
    }

    @Override
    public RoomRobotorHashMap moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
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
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
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
        return roomtidyUpRobotHashMap;

    }


    @Override
    public RoomRobotorHashMap moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
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
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
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
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveConnection(UUID tidyRobot, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        for (Connection connection : connectionList.getConnectionList()) {
            canDoAction = coordinates.equals(connection.getSourceCoordinate());
            if (canDoAction) {
                coordinates = connection.getDestinationCoordinate();
                roomtidyUpRobotHashMap.getRoomtidyUpRobotHashMap().put(tidyRobot, connection.getDestinationRoomId());
                break;
            }
        }

        return roomtidyUpRobotHashMap;
    }


    @Override
    public RoomRobotorHashMap placeRobot(UUID roomUUID, TidyUpRoboterList tidyUpRoboterList, RoomRobotorHashMap roomRobotorHashMap, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
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
        roomRobotorHashMap.getRoomtidyUpRobotHashMap().put(roboterUUID, roomUUID);
        return roomRobotorHashMap;
    }

    public UUID getRoboterUUID() {
        return roboterUUID;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }
    public boolean isCanDoAction() {
        return canDoAction;
    }

}
