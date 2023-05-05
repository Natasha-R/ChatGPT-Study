package thkoeln.st.st2praktikum.exercise.miningmachine.domain;


import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;

public interface Movable {

    void move(CommandType direction);

}
