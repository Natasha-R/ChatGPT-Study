package thkoeln.st.st2praktikum.exercise.services;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Cloud;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.OrderType;
import thkoeln.st.st2praktikum.exercise.types.GeometricPosition;

import java.util.UUID;

@Service
public class ObstacleService {
    public static Boolean checkObstacleCoordinatesInOrder(Integer start, Integer end) {
        return start < end;
    }

    public static boolean checkHorizontalObstacles(UUID cleaningDeviceSpaceId, Coordinate cleaningDeviceCoordinate, Cloud cloud, OrderType orderType) throws IllegalStateException {
        switch (orderType) {
            case NORTH:
                if ((cleaningDeviceCoordinate.getY() + 1) == cloud.getSpaces().findById(cleaningDeviceSpaceId).get().getHeight()) {
                    return false;
                }
                break;
            case SOUTH:
                if ((cleaningDeviceCoordinate.getY() - 1) < 0) {
                    return false;
                }
                break;
        }
        for (Obstacle obstacle : cloud.getSpaces().findById(cleaningDeviceSpaceId).get().getObstacles()) {
            if (obstacle.getGeometricPosition().equals(GeometricPosition.HORIZONTAL)) {
                switch (orderType) {
                    case NORTH:
                        if ((cleaningDeviceCoordinate.getY() + 1) == obstacle.getStart().getY()) {
                            return cleaningDeviceCoordinate.getX() < obstacle.getStart().getX() || cleaningDeviceCoordinate.getX() >= obstacle.getEnd().getX();
                        } else {
                            return true;
                        }
                    case SOUTH:
                        if ((cleaningDeviceCoordinate.getY() - 1) < 0) {
                            return false;
                        }
                        if (cleaningDeviceCoordinate.getY().equals(obstacle.getStart().getY())) {
                            return cleaningDeviceCoordinate.getX() < obstacle.getStart().getX() || cleaningDeviceCoordinate.getX() >= obstacle.getEnd().getX();
                        } else {
                            return true;
                        }
                }
            }
        }
        return true;
    }

    public static boolean checkVerticalObstacles(UUID cleaningDeviceSpaceId, Coordinate cleaningDeviceCoordinate, Cloud cloud, OrderType orderType) throws IllegalStateException {
        switch (orderType) {
            case EAST:
                if ((cleaningDeviceCoordinate.getX() + 1) == cloud.getSpaces().findById(cleaningDeviceSpaceId).get().getWidth()) {
                    return false;
                }
                break;
            case WEST:
                if ((cleaningDeviceCoordinate.getX() - 1) < 0) {
                    return false;
                }
                break;
        }
        for (Obstacle obstacle : cloud.getSpaces().findById(cleaningDeviceSpaceId).get().getObstacles()) {
            if (obstacle.getGeometricPosition().equals(GeometricPosition.VERTICAL)) {
                switch (orderType) {
                    case EAST:
                        if ((cleaningDeviceCoordinate.getX() + 1) == obstacle.getStart().getX()) {
                            return cleaningDeviceCoordinate.getY() < obstacle.getStart().getY() || cleaningDeviceCoordinate.getY() >= obstacle.getEnd().getY();
                        } else {
                            return true;
                        }
                    case WEST:
                        if (cleaningDeviceCoordinate.getX().equals(obstacle.getStart().getX())) {
                            return cleaningDeviceCoordinate.getY() < obstacle.getStart().getY() || cleaningDeviceCoordinate.getY() >= obstacle.getEnd().getY();
                        } else {
                            return true;
                        }
                }
            }
        }
        return true;
    }
}