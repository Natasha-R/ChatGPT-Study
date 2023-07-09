package thkoeln.st.st2praktikum.exercise.selfdefinedsupport;

import lombok.Getter;

public class CoordinatePair {
    @Getter
    private Integer xCoordinate;
    @Getter
    private Integer yCoordinate;

public void setCoordinatePair(Integer xCoordinate, Integer yCoordinate){
    this.xCoordinate=xCoordinate;
    this.yCoordinate=yCoordinate;
}

}
