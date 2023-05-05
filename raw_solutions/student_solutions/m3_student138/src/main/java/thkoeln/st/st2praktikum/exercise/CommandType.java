package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public enum CommandType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER
}
