package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
@Embeddable
public enum CommandType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER,
    INVALID
}
