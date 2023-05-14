package thkoeln.st.st2praktikum.exercise;

public class Coordinate {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int y;
    private int x;


    Coordinate(String commandstring){
        String tmp = commandstring.replace("(","").replace(")","");
        String[] data = tmp.split(",");
        this.y = Integer.parseInt(data[1]);
        this.x = Integer.parseInt(data[0]);

    }

    Coordinate(Coordinate coordinate){
        this.y = coordinate.y;
        this.x = coordinate.x;
    }

    Coordinate(int y , int x){
        this.y = y;
        this.x = x;
    }


    @Override
    public String toString() {
        return "(" + this.x + ',' + this.y + ')';
    }

    public boolean shareXAxis(Coordinate coordinate){
        return this.getX() == coordinate.getX();
    }
    public boolean shareYAxis(Coordinate coordinate){
        return this.getY() == coordinate.getY();
    }

    public boolean equals(Coordinate coordinate){
        return this.shareYAxis(coordinate) && this.shareXAxis(coordinate);
    }

}
