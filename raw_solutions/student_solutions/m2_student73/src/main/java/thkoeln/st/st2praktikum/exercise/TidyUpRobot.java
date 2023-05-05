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

    public Boolean commandDecider(Task task, HashMap<UUID, Room> roomFinder) {

        if (task.getTaskType() == TaskType.TRANSPORT) {
            return teleport(task, roomFinder.get(getCurrentRoom()) ,roomFinder.get(task.getGridId()));
        } else if (task.getTaskType() == TaskType.ENTER) {
            return placeInRoom(roomFinder.get(task.getGridId()));
        } else return moveTo(task, roomFinder.get(getCurrentRoom()));
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

    @Override
    public Boolean moveTo(Task task, Room room) {
        int stepsToDo = task.getNumberOfSteps();
        TaskType direction = task.getTaskType();
        switch (direction) {
            case NORTH:
                 return moveNorth(stepsToDo, room);
            case EAST:
                 return moveEast(stepsToDo, room);
            case SOUTH:
                return moveSouth(stepsToDo, room);
            case WEST:
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
    public Boolean teleport(Task task, Room currentRoom, Room travelToRoom) {
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

    public Vector2D getCurrentPositionAsVector2D() {
        int[] position = getCurrentPosition();
        return new Vector2D(position[0], position[1]);
    }

    @Override
    public UUID getUID() {
        return robotID;
    }
}

