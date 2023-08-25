package thkoeln.st.st2praktikum.exercise.world2.field;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.world2.connection.Connection;

import java.util.*;

public class Field {

    @Getter
    private final UUID fieldId = UUID.randomUUID();

    @Getter
    private final Integer width;

    @Getter
    private final Integer height;

    @Getter
    private final List<Barrier> barriers = new ArrayList<>();

    @Getter
    private final List<Point> points = new ArrayList<>();

    @Getter final HashMap<UUID, Connection> connections = new HashMap<>();


    public Field(Integer height, Integer width){
        this.width = width;
        this.height = height;
        this.fillPoints();
    }

    public void addBarrier(Barrier barrier) {
        this.barriers.add(barrier);
    }
    public void addConnection(Connection connection) throws RuntimeException {
        this.connections.put(connection.getConnectionId(), connection);
    }

    private void fillPoints(){
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                points.add(new Point(x,y));
            }
        }
    }
}
