package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Createable {

    UUID createConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate);
    UUID createSpace(Integer height, Integer width);
    UUID createCleaningDevice(String name);
    void createBarrier(UUID space, String barrier);
}
