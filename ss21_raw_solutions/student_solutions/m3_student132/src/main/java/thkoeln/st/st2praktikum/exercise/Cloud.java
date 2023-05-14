package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.space.SpaceRepository;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public interface Cloud {
    static boolean checkCleaningDevicesInDirection(UUID spaceId, Coordinate coordinate, Cloud cloud, OrderType orderType, UUID cleaningDeviceId) throws IllegalStateException {

        AtomicReference<Boolean> result = new AtomicReference<>(false);

        cloud.getCleaningDevices().findAll().forEach(cleaningDeviceEntry -> {
            if (cleaningDeviceEntry.getSpawned() && !cleaningDeviceEntry.getCleaningDeviceId().equals(cleaningDeviceId)) {
                switch (orderType) {
                    case NORTH:
                        result.set(cleaningDeviceEntry.getSpaceId().equals(spaceId) && cleaningDeviceEntry.getCoordinate().equals(new Coordinate(coordinate.getX(), (coordinate.getY()) + 1)));
                        break;
                    case EAST:
                        result.set(cleaningDeviceEntry.getSpaceId().equals(spaceId) && cleaningDeviceEntry.getCoordinate().equals(new Coordinate((coordinate.getX() + 1), coordinate.getY())));
                        break;
                    case SOUTH:
                        result.set(cleaningDeviceEntry.getSpaceId().equals(spaceId) && cleaningDeviceEntry.getCoordinate().equals(new Coordinate(coordinate.getX(), (coordinate.getY()) - 1)));
                        break;
                    case WEST:
                        result.set(cleaningDeviceEntry.getSpaceId().equals(spaceId) && cleaningDeviceEntry.getCoordinate().equals(new Coordinate((coordinate.getX() - 1), coordinate.getY())));
                        break;
                }
            }
        });
        return result.get();
    }

    CleaningDeviceRepository getCleaningDevices();

    SpaceRepository getSpaces();

}
