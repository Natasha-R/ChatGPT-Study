package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

public class TidyUpRobot implements executeCommand, placeRobot, move {
    private UUID roboterUUID;
    private String name;
    private int[] coordinates = {0, 0};
    private String stringCoordinates = "(0,0)";
    private boolean canDoAction = true;

    public TidyUpRobot(String name, UUID roboterUUID) {
        this.name = name;
        this.roboterUUID = roboterUUID;

    }


    @Override
    public RoomRobotorHashMap executeCommand(String moveCommand, String steps, String UUID, TidyUpRoboterList tidyUpRobotList, RoomList roomList, ConnectionList connectionList, RoomRobotorHashMap roomRobotorHashMap, UUID roomWhereRobotIs, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
        switch (moveCommand) {
            case "en":
                roomRobotorHashMap = placeRobot(UUID, tidyUpRobotList, roomRobotorHashMap, tidyRobot, coordinatesForRobots);
                break;
            case "tr":
                roomRobotorHashMap = moveConnection(tidyRobot, tidyUpRobotList, connectionList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case "no":
                roomRobotorHashMap = moveNorth(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case "so":
                roomRobotorHashMap = moveSouth(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case "ea":
                roomRobotorHashMap = moveEast(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
            case "we":
                roomRobotorHashMap = moveWest(steps, tidyUpRobotList, roomList, roomRobotorHashMap, roomWhereRobotIs);
                break;
        }
        return roomRobotorHashMap;
    }

    @Override
    public RoomRobotorHashMap moveNorth(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        int intSteps = Integer.parseInt(steps);
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < intSteps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates[1] + 1 == barrier.getCoordinates()[0][1]) {
                            if (coordinates[0] >= barrier.getCoordinates()[0][0] && coordinates[0] < barrier.getCoordinates()[1][0]) {
                                //if (coordinates[0] != barrier.coordinates[0][0] && coordinates[0] != barrier.coordinates[1][0]) {
                                theBreak = true;
                                //}
                            }
                        }
                    }

                    if (!theBreak) {
                        coordinates[1] = coordinates[1] + 1;
                    }
                }
            }
        }
        stringCoordinates = "(" + coordinates[0] + "," + coordinates[1] + ")";
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveSouth(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        int intSteps = Integer.parseInt(steps);
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < intSteps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates[1] - 1 == barrier.getCoordinates()[0][1]) {
                            if (coordinates[0] >= barrier.getCoordinates()[0][0] && coordinates[0] < barrier.getCoordinates()[1][0]) {
                                //if (coordinates[0] != barrier.coordinates[0][0] && coordinates[0] != barrier.coordinates[1][0]) {
                                theBreak = true;
                                //}
                            }

                        }
                    }
                    if (!theBreak) {
                        coordinates[1] = coordinates[1] - 1;

                    }

                }
            }
        }
        if (theBreak) {
            coordinates[1] = coordinates[1] - 1;
        }
        stringCoordinates = "(" + coordinates[0] + "," + coordinates[1] + ")";
        return roomtidyUpRobotHashMap;

    }


    @Override
    public RoomRobotorHashMap moveEast(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        int intSteps = Integer.parseInt(steps);
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < intSteps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates[0] + 1 == barrier.getCoordinates()[0][0]) {
                            if (coordinates[1] >= barrier.getCoordinates()[0][1] && coordinates[0] < barrier.getCoordinates()[1][1]) {
                                // if (coordinates[1] != barrier.coordinates[0][1] && coordinates[0] != barrier.coordinates[1][1]) {
                                theBreak = true;
                                //}
                            }
                        }

                    }
                    if (!theBreak) {
                        coordinates[0] = coordinates[0] + 1;
                    }
                }

            }
        }
        stringCoordinates = "(" + coordinates[0] + "," + coordinates[1] + ")";
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveWest(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        int intSteps = Integer.parseInt(steps);
        boolean theBreak = false;
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID().equals(roomWhereRobotIs)) {
                for (int i = 0; i < intSteps; i++) {
                    for (Barrier barrier : room.getBarrierList()) {
                        if (coordinates[0] - 1 == barrier.getCoordinates()[0][0]) {
                            if (coordinates[1] >= barrier.getCoordinates()[0][1] && coordinates[0] < barrier.getCoordinates()[1][1]) {
                                //if (coordinates[1] != barrier.coordinates[0][1] && coordinates[0] != barrier.coordinates[1][1]) {
                                theBreak = true;
                                //}
                            }
                        }

                    }
                    if (!theBreak) {
                        coordinates[0] = coordinates[0] - 1;
                    }
                }

            }
        }
        if (theBreak) {
            coordinates[0] = coordinates[0] - 1;
        }
        stringCoordinates = "(" + coordinates[0] + "," + coordinates[1] + ")";
        return roomtidyUpRobotHashMap;
    }

    @Override
    public RoomRobotorHashMap moveConnection(UUID tidyRobot, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs) {
        for (Connection connection : connectionList.getConnectionList()) {
            canDoAction = Arrays.equals(coordinates, connection.getStartCoordinates());
            if (canDoAction) {
                coordinates = connection.getEndCoordinates();
                stringCoordinates = "(" + coordinates[0] + "," + coordinates[1] + ")";
                roomtidyUpRobotHashMap.getRoomtidyUpRobotHashMap().put(tidyRobot, connection.getDestinationRoomId());
                break;
            }
        }

        return roomtidyUpRobotHashMap;
    }


    @Override
    public RoomRobotorHashMap placeRobot(String roomUUID, TidyUpRoboterList tidyUpRoboterList, RoomRobotorHashMap roomRobotorHashMap, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots) {
        boolean breaker = false;
        int[] tempcoordinates = coordinatesForRobots.getCoordinatesForRobots().
                get(tidyUpRoboterList.getTidyUpRobotsList().get(0).roboterUUID);

        for (TidyUpRobot tidyUpRobot : tidyUpRoboterList.getTidyUpRobotsList()) {
            if (tempcoordinates != null) {
                if (tempcoordinates[0] == tidyUpRobot.coordinates[0]) {
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
        roomRobotorHashMap.getRoomtidyUpRobotHashMap().put(roboterUUID, UUID.fromString(roomUUID));
        return roomRobotorHashMap;
    }

    public UUID getRoboterUUID() {
        return roboterUUID;
    }

    public String getName() {
        return name;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public String getStringCoordinates() {
        return stringCoordinates;
    }

    public boolean isCanDoAction() {
        return canDoAction;
    }

}
