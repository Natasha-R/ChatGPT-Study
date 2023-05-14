package thkoeln.st.st2praktikum.exercise;

public class Cell implements CellInterface {
    private Boolean cellStateIsFree = Boolean.TRUE;

    @Override
    public void setCellStateIsFree(Boolean cellStateIsFree) {
        this.cellStateIsFree = cellStateIsFree;
    }

    @Override
    public Boolean getCellStateisFree() {
        return cellStateIsFree;
    }
}
