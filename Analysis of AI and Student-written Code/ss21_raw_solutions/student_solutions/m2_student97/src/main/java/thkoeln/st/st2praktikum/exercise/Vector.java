package thkoeln.st.st2praktikum.exercise;

public class Vector {


    private Integer x;
    private Integer y;


    public Vector(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX () {
        return this.x;
    }


    public Integer getY () {
        return this.y;
    }


    public void setX (Integer value) {
        this.x = value;
    }


    public void setY (Integer value) {
        this.y = value;
    }
}
