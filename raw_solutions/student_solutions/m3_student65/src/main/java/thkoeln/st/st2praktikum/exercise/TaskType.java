package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;

@Embeddable
public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER
}
