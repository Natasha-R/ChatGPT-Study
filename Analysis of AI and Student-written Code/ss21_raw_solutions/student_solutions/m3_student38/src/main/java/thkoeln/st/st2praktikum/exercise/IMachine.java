package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

interface IMachine{
    boolean executeCommand(Command command, HashMap<UUID,Field> fields,HashMap<UUID,Connection> connections);
    boolean move(Command command, Field field);
    boolean spawnOnField(Field field);
    boolean transportToField(Connection connection);
}
