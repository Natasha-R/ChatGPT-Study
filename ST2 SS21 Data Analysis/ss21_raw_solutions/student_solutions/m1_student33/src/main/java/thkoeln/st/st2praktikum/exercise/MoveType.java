package thkoeln.st.st2praktikum.exercise;

public enum MoveType {
    no,
    so,
    ea,
    we,
    tr,
    en;

    public boolean checkMoveType(){
        return (this == no || this == so || this == ea || this == we);
    }
}
