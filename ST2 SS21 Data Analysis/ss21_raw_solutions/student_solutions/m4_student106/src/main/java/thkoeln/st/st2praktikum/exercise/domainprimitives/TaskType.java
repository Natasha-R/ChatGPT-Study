package thkoeln.st.st2praktikum.exercise.domainprimitives;

public enum TaskType {
    NORTH,
    WEST,
    SOUTH,
    EAST,
    TRANSPORT,
    ENTER;

    public Boolean isMoveCommand() {
        return (this == NORTH || this == EAST || this == SOUTH || this == WEST);
    }
}