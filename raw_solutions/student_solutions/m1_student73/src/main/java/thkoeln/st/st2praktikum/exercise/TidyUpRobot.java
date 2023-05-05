package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobot implements Moveable, Identifiable {
    private final String name;
    private int[] currentPosition = new int[2];
    private final UUID robotID = UUID.randomUUID();
    private UUID currentRoom = null;
    private boolean isPlaced = false;

    public TidyUpRobot(String nameParam) {
        name = nameParam;
    }

    public Boolean commandDecider(String commandString, HashMap<UUID, Room> roomFinder) {

        String command = commandString.substring(commandString.indexOf('[') + 1, commandString.indexOf(','));

        if (command.equals("tr")) {
            return teleport(commandString, roomFinder.get(getCurrentRoom()) ,roomFinder.get(UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.indexOf(']')))));
        } else if (command.equals("en")) {
            return placeInRoom(roomFinder.get(UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.indexOf(']')))));
        } else return moveTo(commandString, roomFinder.get(getCurrentRoom()));
    }

    @Override
    public Boolean moveTo(String moveCommandString, Room room) {
        int stepsToDo = Integer.parseInt(moveCommandString.substring(moveCommandString.indexOf(',')+1,moveCommandString.indexOf(']')));
        String direction = moveCommandString.substring(moveCommandString.indexOf('[')+1,moveCommandString.indexOf(','));
        switch (direction) {
            case "no":
                 return moveNorth(stepsToDo, room);
            case "ea":
                 return moveEast(stepsToDo, room);
            case "so":
                return moveSouth(stepsToDo, room);
            case "we":
                return moveWest(stepsToDo, room);
            default:
                return true;
        }
    }

    public Boolean moveNorth(int stepsToDo, Room room) {
        for (int i = 0; i < stepsToDo; i++) {
            if (currentPosition[1] == room.getHeight()-1){
                return true;
            }
            if(room.getCoordinate(currentPosition[0], currentPosition[1] + 1).isHasRobot()) {
                return true;
            } else if(!room.getCoordinate(currentPosition[0], currentPosition[1] + 1).isHasHorizontalBarrier()) {
                setCurrentPosition(currentPosition[0], currentPosition[1]+1, room);
            } else if(!room.getCoordinate(currentPosition[0] + 1, currentPosition[1] + 1).isHasHorizontalBarrier()) {
                setCurrentPosition(currentPosition[0], currentPosition[1]+1, room);
            } else return true;
        }
        return true;
    }

    public Boolean moveEast(int stepsToDo, Room room) {
        for (int i = 0; i < stepsToDo; i++) {
            if (currentPosition[0] == room.getWidth()-1){
                return true;
            }
            if(room.getCoordinate(currentPosition[0] + 1, currentPosition[1]).isHasRobot()) {
                return true;
            } else if(!room.getCoordinate(currentPosition[0] + 1, currentPosition[1]).isHasVerticalBarrier()) {
                setCurrentPosition(currentPosition[0]+1, currentPosition[1], room);
            } else if(!room.getCoordinate(currentPosition[0] + 1, currentPosition[1] + 1).isHasVerticalBarrier()) {
                setCurrentPosition(currentPosition[0]+1, currentPosition[1], room);
            } else return true;
        }
        return true;
    }

    public Boolean moveSouth(int stepsToDo, Room room) {
        if(!room.getCoordinate(currentPosition[0], currentPosition[1]).isHasHorizontalBarrier() || !room.getCoordinate(currentPosition[0] + 1, currentPosition[1]).isHasHorizontalBarrier()) {
            for (int i = 0; i < stepsToDo; i++) {
                if(currentPosition[1] == 0) {
                    return true;
                }
                if (room.getCoordinate(currentPosition[0], currentPosition[1] - 1).isHasRobot()) {
                    return true;
                } else if(!room.getCoordinate(currentPosition[0], currentPosition[1] - 1).isHasHorizontalBarrier()) {
                    setCurrentPosition(currentPosition[0], currentPosition[1]-1, room);
                } else if(room.getCoordinate(currentPosition[0], currentPosition[1] - 1).isHasHorizontalBarrier()) {
                    setCurrentPosition(currentPosition[0], currentPosition[1]-1, room);
                    if(room.getCoordinate(currentPosition[0] + 1, currentPosition[1]).isHasHorizontalBarrier()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public Boolean moveWest(int stepsToDo, Room room) {
        if(!room.getCoordinate(currentPosition[0], currentPosition[1]).isHasVerticalBarrier() || !room.getCoordinate(currentPosition[0], currentPosition[1]+1).isHasVerticalBarrier()) {
            for (int i = 0; i < stepsToDo; i++) {
                if(currentPosition[0] == 0) {
                    return true;
                }
                if (room.getCoordinate(currentPosition[0]-1, currentPosition[1]).isHasRobot()) {
                    return true;
                } else if(!room.getCoordinate(currentPosition[0]-1, currentPosition[1]).isHasVerticalBarrier()) {
                    setCurrentPosition(currentPosition[0]-1, currentPosition[1], room);
                } else if(room.getCoordinate(currentPosition[0]-1, currentPosition[1]).isHasVerticalBarrier()) {
                    setCurrentPosition(currentPosition[0]-1, currentPosition[1], room);
                    if(room.getCoordinate(currentPosition[0], currentPosition[1]+1).isHasVerticalBarrier()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean teleport(String teleportCommandString, Room currentRoom, Room travelToRoom) {
        if(currentRoom.getCoordinate(currentPosition[0], currentPosition[1]).isHasConnection()) {
            this.setCurrentRoom(travelToRoom.getUID());
            this.setCurrentPosition(currentRoom.getCoordinate(currentPosition[0], currentPosition[1]).getConnectionCoordinate()[0], currentRoom.getCoordinate(currentPosition[0], currentPosition[1]).getConnectionCoordinate()[1], currentRoom);
            changeFloorTileHasRobot(travelToRoom);
            return true;
        } else return false;
    }

    public void changeFloorTileHasRobot(Room room) {
        room.getCoordinate(currentPosition[0], currentPosition[1]).setHasRobot();
    }

    @Override
    public Boolean placeInRoom(Room room) {
        if(isPlaced) {
            return false;
        }
        if (room.getCoordinate(0, 0).isHasRobot()) {
            return false;
        } else {
            this.setCurrentRoom(room.getUID());
            this.setCurrentPosition(0, 0, room);
            isPlaced = true;
            return true;
        }
    }

    public UUID getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(UUID room) {
        currentRoom = room;
    }

    public void setCurrentPosition(int x, int y, Room room) {
        if(isPlaced){
            changeFloorTileHasRobot(room);
        }
        currentPosition[0] = x;
        currentPosition[1] = y;
        if(this.getCurrentRoom() == room.getUID()) {
            changeFloorTileHasRobot(room);
        }
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public String getCurrentPositionAsString() {
        int[] position = getCurrentPosition();
        return '(' + Integer.toString(position[0]) + ',' + Integer.toString(position[1]) + ')';
    }

    public String getName() {
        return name;
    }

    @Override
    public UUID getUID() {
        return robotID;
    }
}

