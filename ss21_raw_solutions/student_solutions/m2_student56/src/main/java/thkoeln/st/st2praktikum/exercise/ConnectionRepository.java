package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class ConnectionRepository {

    private List<Connection> connections = new ArrayList<>();

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections ( List<Connection> connections){
        this.connections = connections;
    }
}
