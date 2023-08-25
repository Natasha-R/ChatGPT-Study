package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class ConnectionList implements AddobjectInterface {
    private List<Connection> connectionlist = new ArrayList<Connection>();

    public List<Connection> getConnectionlist() {
        return connectionlist;
    }

    @Override
    public void add(Object connection) {
        connectionlist.add((Connection) connection);

    }
}
