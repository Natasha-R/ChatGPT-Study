package thkoeln.st.st2praktikum.entities;

public class Cell {

    private Integer x;
    private Integer y;

    public Cell(String cellString){
        fetchXAndY(cellString);
    }

    public Cell(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object cell){
        if(cell instanceof Cell){
            return ((Cell) cell).getX().equals(x) && ((Cell) cell).getY().equals(y);
        }
        return false;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    private void fetchXAndY(String cellString){
        String[] xAndYArray = cellString.split(",");
        this.x = Integer.parseInt(xAndYArray[0].substring(1));
        this.y = Integer.parseInt(xAndYArray[1].substring(0,xAndYArray[1].length()-1));
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
