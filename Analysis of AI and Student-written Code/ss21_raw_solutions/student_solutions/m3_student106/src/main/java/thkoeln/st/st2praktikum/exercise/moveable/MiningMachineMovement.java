package thkoeln.st.st2praktikum.exercise.moveable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.exercise.transportSystem.*;
import thkoeln.st.st2praktikum.exercise.field.FieldRepository;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class MiningMachineMovement {
    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    MiningMachineRepository miningMachineRepository;

    public boolean move(Task task, UUID miningMachineId) {
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).get();
        Point wishedPosition;
        if (task.getTaskType().isMoveCommand()) {
            wishedPosition = calculateNewPosition(task.getTaskType(), task.getNumberOfSteps(), miningMachine);
            wishedPosition = fieldRepository.findById(miningMachine.getFieldId()).get().correctPointToBeWithinBoarders(wishedPosition);
            wishedPosition = correctByObsticaleInTheWay(task.getTaskType(), wishedPosition, miningMachine);
            wishedPosition = correctByMovableInTheWay(task.getTaskType(), wishedPosition, miningMachine);

            task.setStatus(miningMachine.getPoint() != wishedPosition);
            miningMachine.setPoint(wishedPosition);
            miningMachineRepository.save(miningMachine);
        } else if (TaskType.ENTER == task.getTaskType()) {
            task.setStatus(positionForFirstTime(task.getFieldId(), miningMachine));
        } else if (TaskType.TRANSPORT == task.getTaskType()) {
            task.setStatus(positioningByTransition(task.getFieldId(), miningMachine));
        } else return false;
        return task.getStatus();
    }

    private Point calculateNewPosition(TaskType taskType, int numberOfSteps, MiningMachine miningMachine) {
        Point point = new Point(miningMachine.getPoint().getX(), miningMachine.getPoint().getY());
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

    private Point correctByObsticaleInTheWay(TaskType taskType, Point wishedPosition, MiningMachine miningMachine) {
        Point newPoint = new Point(wishedPosition.getX(), wishedPosition.getY());
        for (Obstacle obstacle : fieldRepository.findById(miningMachine.getFieldId()).get().getObstacles()) {
            if (taskType.equals(TaskType.NORTH) || taskType.equals(TaskType.SOUTH)) {
                newPoint = correctVerticalForObsticalIsInTheWay(obstacle, taskType, wishedPosition, miningMachine);
            } else if (taskType.equals(TaskType.EAST) || taskType.equals(TaskType.WEST)) {
                newPoint = correctHorizontalForObsticalIsInTheWay(obstacle, taskType, wishedPosition, miningMachine);
            }
        }
        return newPoint;
    }

    private Point correctByMovableInTheWay(TaskType taskType, Point wishedPosition, MiningMachine miningMachine) {
        if (checkPositionIsBlockedByMovable(wishedPosition, miningMachine)) {
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
            correctByMovableInTheWay(taskType, wishedPosition, miningMachine);
        }
        return wishedPosition;
    }

    private boolean positionForFirstTime(UUID wishedDestinationField, MiningMachine miningMachine) {
        if (miningMachine.getFieldId() == null) {
            miningMachine.setFieldId(wishedDestinationField);
            if (!checkPositionIsBlockedByMovable(new Point(0, 0), miningMachine)) {
                miningMachine.setPoint(new Point(0, 0));
                miningMachineRepository.save(miningMachine);
                return true;
            }
        }
        miningMachine.setFieldId(null);
        return false;
    }

    private boolean positioningByTransition(UUID wishedDestinationFieldId, MiningMachine miningMachine) {
        UUID orignFieldId = miningMachine.getFieldId();
        for (Connection connection : connectionRepository.findBySourceFieldId(miningMachine.getFieldId())) {
            if (miningMachine.getPoint().equals(connection.getSourcePoint())
                    && wishedDestinationFieldId.equals(connection.getDestinationFieldId())) {
                miningMachine.setFieldId(wishedDestinationFieldId);
                if (!checkPositionIsBlockedByMovable(connection.getDestinationPoint(), miningMachine)) {
                    miningMachine.setPoint(connection.getDestinationPoint());
                    miningMachineRepository.save(miningMachine);
                    return true;
                }
            }
        }
        miningMachine.setFieldId(orignFieldId);
        return false;
    }

    private Point correctVerticalForObsticalIsInTheWay(
            Obstacle obstacle, TaskType taskType, Point wishedPosition, MiningMachine miningMachine) {
        ArrayList<Point> blockedPoints = obstacle.getBlockedPointsAccordingToDirection(taskType);
        if (blockedPoints == null) return wishedPosition;
        if (miningMachine.getPoint().getX() >= blockedPoints.get(0).getX()
                && miningMachine.getPoint().getX() < blockedPoints.get(blockedPoints.size() - 1).getX()
                && miningMachine.getPoint().getY() < blockedPoints.get(0).getY()
                && wishedPosition.getY() >= blockedPoints.get(0).getY()) {
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

    private Point correctHorizontalForObsticalIsInTheWay(
            Obstacle obstacle, TaskType taskType, Point wishedPosition, MiningMachine miningMachine) {
        ArrayList<Point> blockedPoints = obstacle.getBlockedPointsAccordingToDirection(taskType);
        if (blockedPoints == null) return wishedPosition;
        if (miningMachine.getPoint().getY() >= blockedPoints.get(0).getY()
                && miningMachine.getPoint().getY() < blockedPoints.get(blockedPoints.size() - 1).getY()
                && miningMachine.getPoint().getX() < blockedPoints.get(0).getX()
                && wishedPosition.getX() >= blockedPoints.get(0).getX()) {
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

    private boolean checkPositionIsBlockedByMovable(Point wishedPosition, MiningMachine miningMachine) {
        //for (MiningMachine miningMachineListItem : miningMachineRepository.findAll()){
        for (MiningMachine miningMachineListItem : miningMachineRepository.findByFieldId(miningMachine.getFieldId())) {
            if (miningMachineListItem.getFieldId() != null
                    && miningMachineListItem.getUuid() != miningMachine.getUuid()) {
                for (Point blockedPoint : miningMachineListItem.getBlockedPoints()) {
                    if (blockedPoint.equals(wishedPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
