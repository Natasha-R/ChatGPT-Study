package thkoeln.st.st2praktikum.exercise;

public class Point {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int px, int py){
        x=px;
        y=py;
    }

    public void updateY(int steps) {
        y += steps;
    }

    public void updateX(int steps) {
        x += steps;
    }

    public Point(String Coordinate){
        String[] splitted =Coordinate.split(",");
        x=Integer.parseInt(splitted[0]);
        y=Integer.parseInt(splitted[1]);
    }
}
