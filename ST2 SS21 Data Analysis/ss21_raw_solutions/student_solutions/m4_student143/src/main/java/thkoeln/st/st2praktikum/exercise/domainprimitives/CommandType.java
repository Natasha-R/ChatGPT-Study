package thkoeln.st.st2praktikum.exercise.domainprimitives;

import javax.persistence.Embeddable;

@Embeddable
public enum CommandType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER
}

/*
public enum CommandType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER
}*/
