package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface ICommandExecution
{
    boolean executeCommand(Order order, HashMap<UUID, Room> rooms, List<Connection> connections);
}
