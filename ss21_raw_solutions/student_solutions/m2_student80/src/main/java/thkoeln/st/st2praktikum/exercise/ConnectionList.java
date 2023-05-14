package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class ConnectionList implements addStuff {

    private List<Connection> connectionList = new ArrayList<Connection>();


    @Override
    public void add(Object toAdd) {
        connectionList.add((Connection) toAdd);
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }
}
