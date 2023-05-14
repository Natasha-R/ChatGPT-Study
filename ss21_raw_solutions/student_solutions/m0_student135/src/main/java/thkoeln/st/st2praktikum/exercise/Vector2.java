package thkoeln.st.st2praktikum.exercise;

public class Vector2 {
    int x, y;
    Vector2(int _x, int _y){
        x = _x;
        y = _y;
    }

    public Vector2 add(Vector2 c){
        x += c.x;
        y += c.y;
        return this;
    }

    public Vector2 multiply(int skalar){
        x *= skalar;
        y *= skalar;
        return this;
    }

    public Vector2 clone(){
        return new Vector2(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + x + ',' + y + ')';
    }

    public double length(){
        return Math.sqrt(x*x + y*y);
    }
}
