package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        keineNegativenZahlen();
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        String xUndYDurchKommaGetrennt = vector2DString.replaceAll("\\(", "");
        xUndYDurchKommaGetrennt = xUndYDurchKommaGetrennt.replaceAll("\\)", "");
        String[] xUndyArray = xUndYDurchKommaGetrennt.split(",");
        if (xUndyArray[0] != null && xUndyArray[1] != null) {
            if(xUndyArray.length==2) {
                try {
                    this.x = Integer.parseInt(xUndyArray[0]);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException();
                }
                try {
                    this.y = Integer.parseInt(xUndyArray[1]);
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException();
                }
            }
            else{
                throw new RuntimeException();
            }
        }
        else{
            throw new RuntimeException();
        }
        keineNegativenZahlen();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    private void keineNegativenZahlen(){
        if(x<0 || y<0)throw new RuntimeException();
    }
}
