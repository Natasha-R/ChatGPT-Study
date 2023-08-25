package thkoeln.st.st2praktikum.exercise.containerClasses;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.DirectionsType;

import javax.persistence.Embeddable;

@Embeddable
public class CoordinateWiseObstacle {

    @Getter
    private final Coordinate coordinate;
    @Getter
    private final DirectionsType direction;

    public CoordinateWiseObstacle(){
        this.coordinate = null;
        direction = null;
    }

    public CoordinateWiseObstacle(Coordinate coordinate, DirectionsType direction){
        this.coordinate = coordinate;
        this.direction = direction;
    }


}
