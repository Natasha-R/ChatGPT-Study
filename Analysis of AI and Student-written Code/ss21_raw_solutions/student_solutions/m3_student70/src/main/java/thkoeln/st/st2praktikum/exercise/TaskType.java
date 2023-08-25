package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;

@Embeddable

public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;
    public boolean isCommand () {
        return ( this == NORTH || this == WEST || this == SOUTH
                || this == EAST || this == TRANSPORT || this == ENTER );
    }
}
