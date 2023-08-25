package thkoeln.st.st2praktikum.exercise.movable;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.exercise.field.Connection;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Getter
public class MiningMachine implements Moveable, Blockable{
    @Id
    private final UUID uuid = UUID.randomUUID();
    @Setter
    private String name;

    private UUID fieldId;
    private Point position = new Point("(0,0)");


    public MiningMachine(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Point> getBlockedPoints() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(this.position);
        return list;
    }

    @Override
    public boolean move(Task task) {
        Point wishedPosition;
        if (!task.isStatus()) return false;
        if (task.getTaskType().isMoveCommand()) {
            wishedPosition = calculateNewPosition(task.getTaskType(), task.getNumberOfSteps());
            wishedPosition = DataStorage.getFieldMap().get(fieldId).correctPointToBeWithinBoarders(wishedPosition);
            wishedPosition = correctByObsticaleInTheWay(task.getTaskType(), wishedPosition);
            wishedPosition = correctByMovableInTheWay(wishedPosition, task.getTaskType(), fieldId);

            task.setStatus(position != wishedPosition);
            position = wishedPosition;
            MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
            DataStorage.getMoveableMap().replace(uuid, miningMachine);
        } else if (TaskType.ENTER == task.getTaskType()) {
            task.setStatus(positionForFirstTime(task.getFieldId()));
        } else if (TaskType.TRANSPORT == task.getTaskType()) {
            task.setStatus(positioningByTransition(task.getFieldId()));
        } else return false;
        return task.isStatus();
    }

    private Point calculateNewPosition(TaskType taskType, int numberOfSteps) {
        Point point = new Point(position.getX(), position.getY());
        switch (taskType) {
            case NORTH:
                point.setY(point.getY() + numberOfSteps);
                break;
            case SOUTH:
                point.setY(point.getY() - numberOfSteps);
                break;
            case EAST:
                point.setX(point.getX() + numberOfSteps);
                break;
            case WEST:
                point.setX(point.getX() - numberOfSteps);
        }
        return point;
    }

    private Point correctByObsticaleInTheWay(TaskType taskType, Point wishedPosition) {
        Point newPoint = new Point(wishedPosition.getX(), wishedPosition.getY());
        for (Obstacle obstacle : DataStorage.getFieldMap().get(fieldId).getObstacles()) {
            if (taskType.equals(TaskType.NORTH) || taskType.equals(TaskType.SOUTH)) {
                newPoint = correctVerticalForObsticalIsInTheWay(obstacle, taskType, newPoint);
            } else if (taskType.equals(TaskType.EAST) || taskType.equals(TaskType.WEST)) {
                newPoint = correctHorizontalForObsticalIsInTheWay(obstacle, taskType, newPoint);
            }
        }
        return newPoint;
    }

    private Point correctByMovableInTheWay(Point wishedPosition, TaskType taskType, UUID fieldId) {
        if (checkPositionIsBlockedByMovable(wishedPosition, fieldId)) {
            switch (taskType) {
                case NORTH:
                    wishedPosition.setY(wishedPosition.getY() - 1);
                    break;
                case SOUTH:
                    wishedPosition.setY(wishedPosition.getY() + 1);
                    break;
                case EAST:
                    wishedPosition.setX(wishedPosition.getX() - 1);
                    break;
                case WEST:
                    wishedPosition.setX(wishedPosition.getX() + 1);
            }
            correctByMovableInTheWay(wishedPosition, taskType, fieldId);
        }
        return wishedPosition;
    }

    private boolean positionForFirstTime(UUID wishedDestinationFieldId) {
        if (fieldId == null) {
            if (!checkPositionIsBlockedByMovable(new Point(0, 0), wishedDestinationFieldId)) {
                fieldId = wishedDestinationFieldId;
                MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
                DataStorage.getMoveableMap().replace(uuid, miningMachine);
                return true;
            }
        }
        return false;
    }

    private boolean positioningByTransition(UUID wishedDestinationFieldId) {
        for (Map.Entry<UUID, Connection> connection : DataStorage.getConnectionMap().entrySet()) {
            if (fieldId.equals(connection.getValue().getSourceFieldId())
                    && position.equals(connection.getValue().getSourcePoint())) {
                if (wishedDestinationFieldId.equals(connection.getValue().getDestinationFieldId())
                        && !checkPositionIsBlockedByMovable(connection.getValue().getDestinationPoint(),
                        connection.getValue().getDestinationFieldId())) {
                    position = connection.getValue().getDestinationPoint();
                    fieldId = connection.getValue().getDestinationFieldId();
                    MiningMachine miningMachine = (MiningMachine) DataStorage.getMoveableMap().get(uuid);
                    DataStorage.getMoveableMap().replace(uuid, miningMachine);
                    return true;
                }
            }
        }
        return false;
    }

    private Point correctVerticalForObsticalIsInTheWay(Obstacle obstacle, TaskType taskType, Point wishedPosition) {
        ArrayList<Point> blockedPoints = obstacle.getBlockedPointsAccordingToDirection(taskType);
        if (blockedPoints == null) return wishedPosition;
        if (position.getX() >= blockedPoints.get(0).getX() && position.getX() < blockedPoints.get(blockedPoints.size() - 1).getX()
                && position.getY() < blockedPoints.get(0).getY() && wishedPosition.getY() >= blockedPoints.get(0).getY()) {
            switch (taskType) {
                case NORTH:
                    wishedPosition.setY(blockedPoints.get(0).getY() - 1);
                    break;
                case SOUTH:
                    wishedPosition.setY(obstacle.getStart().getY() + 1);
            }
        }
        return wishedPosition;
    }

    private Point correctHorizontalForObsticalIsInTheWay(Obstacle obstacle, TaskType taskType, Point wishedPosition) {
        ArrayList<Point> blockedPoints = obstacle.getBlockedPointsAccordingToDirection(taskType);
        if (blockedPoints == null) return wishedPosition;
        if (position.getY() >= blockedPoints.get(0).getY() && position.getY() < blockedPoints.get(blockedPoints.size() - 1).getY()
                && position.getX() < blockedPoints.get(0).getX() && wishedPosition.getX() >= blockedPoints.get(0).getX()) {
            switch (taskType) {
                case EAST:
                    wishedPosition.setX(obstacle.getStart().getX() - 1);
                    break;
                case WEST:
                    wishedPosition.setX(obstacle.getStart().getX() + 1);
            }
        }
        return wishedPosition;
    }

    private boolean checkPositionIsBlockedByMovable(Point wishedPosition, UUID destinationFieldID) {
        for (Map.Entry<UUID, Moveable> moveable : DataStorage.getMoveableMap().entrySet()) {
            if (destinationFieldID.equals(moveable.getValue().getFieldId()) && moveable.getValue().getUuid() != this.uuid) {
                for (Point blockedPoint : moveable.getValue().getBlockedPoints()) {
                    if (blockedPoint.equals(wishedPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}