package thkoeln.st.st2praktikum.exercise;

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
