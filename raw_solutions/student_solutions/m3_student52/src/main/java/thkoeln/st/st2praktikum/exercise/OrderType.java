package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
public enum OrderType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER
}
