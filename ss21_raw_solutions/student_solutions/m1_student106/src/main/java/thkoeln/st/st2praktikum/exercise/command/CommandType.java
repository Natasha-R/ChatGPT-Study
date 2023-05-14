package thkoeln.st.st2praktikum.exercise.command;

public enum CommandType {
    no, ea, so, we, tr, en;

    public boolean isMoveCommand() {
        return (this == no || this == ea || this == so || this == we);
    }
}
