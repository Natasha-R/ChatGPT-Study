package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

public interface TransportAble {

    Boolean transport(Room destinationRoom, Task task);

}
