package thkoeln.st.st2praktikum.exercise;

class Cell{
    boolean wNO;
    boolean wEA;
    boolean wSO;
    boolean wWE;
    boolean busy;

    public Cell(boolean wNO, boolean wEA, boolean wSO, boolean wWE){
        this.wNO = wNO;
        this.wEA = wEA;
        this.wSO = wSO;
        this.wWE = wWE;
        this.busy = false;
    }
}