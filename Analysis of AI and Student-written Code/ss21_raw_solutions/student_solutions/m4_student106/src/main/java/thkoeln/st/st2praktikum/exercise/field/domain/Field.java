package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Field {
    @Id
    private UUID uuid = UUID.randomUUID();
    private Integer width;
    private Integer height;

    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private final List<Obstacle> obstacles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<MiningMachine> occupiedList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private  List<Connection> connectionsList;

    protected Field() {
    }

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public Point correctPointToBeWithinBoarders(Point wishedPosition) {
        if (wishedPosition.getX() >= width) wishedPosition.setX(width - 1);
        else if (wishedPosition.getY() >= height) wishedPosition.setY(height - 1);
        else wishedPosition = wishedPosition.changeNegativeValueTo0();
        return wishedPosition;
    }

    public void ceckPointToBeWithinBoarders(Point wishedPosition) throws RuntimeException {
        if (wishedPosition.getX() >= width || wishedPosition.getY() >= height) {
            throw new RuntimeException("Point is outside of the fields boarders");
        }
        wishedPosition.checkXandYareNotNegative();
    }
}