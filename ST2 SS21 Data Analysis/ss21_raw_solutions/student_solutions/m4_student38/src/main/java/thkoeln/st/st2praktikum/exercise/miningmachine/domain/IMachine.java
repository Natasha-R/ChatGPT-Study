package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

interface IMachine{
    boolean executeCommand(Command command, HashMap<UUID, Field> fields, List<Connection> connections);
    boolean move(Command command, Field field);
    boolean spawnOnField(Field field);
    boolean transportToField(Connection connection, HashMap<UUID,Field> fields);
}
