package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Cuttable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface IObstacle extends Cuttable {
    static final double DEFAULT_OBSTACLE_WIDTH = 0.5;
}
