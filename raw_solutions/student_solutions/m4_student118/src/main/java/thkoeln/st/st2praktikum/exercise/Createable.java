package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;

import java.util.UUID;

public interface Createable {

    UUID createConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate);
    UUID createSpace(Integer height, Integer width);
    UUID createCleaningDevice(String name);
    void createBarrier(UUID space, Barrier barrier);
}
