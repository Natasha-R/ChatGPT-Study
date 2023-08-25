package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Connectionlist implements AddobjectInterface {
    List<Connection> connectionlist = new ArrayList<Connection>();

    @Override
    public void add(Object connection) {
        connectionlist.add((Connection) connection);

    }
}
