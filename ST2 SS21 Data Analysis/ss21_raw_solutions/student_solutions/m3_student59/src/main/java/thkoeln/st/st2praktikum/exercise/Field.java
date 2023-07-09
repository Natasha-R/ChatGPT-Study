package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Point;
import thkoeln.st.st2praktikum.exercise.Connection;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
public class Field {

    @Id
    private final UUID fieldId = UUID.randomUUID();

    private Integer width;

    private Integer height;

    @OneToMany( cascade = {CascadeType.ALL} )
    private final List<Barrier> barriers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<Point> points = new ArrayList<>();

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    final Map<UUID, Connection> connections = new HashMap<>();


    public Field(Integer height, Integer width){
        this.width = width;
        this.height = height;
        this.fillPoints();
    }

    protected Field(){}

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
