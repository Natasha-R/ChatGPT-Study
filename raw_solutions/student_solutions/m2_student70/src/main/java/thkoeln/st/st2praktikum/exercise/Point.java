package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if(x<0||y<0)  throw new UnsupportedOperationException();

        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        if (!pointString.startsWith("(")
                || !pointString.endsWith(")")) throw new UnsupportedOperationException();
        String[] pointdata = pointString.replace("(", "")
                .replace(")", "").split(",");
        int x1 = -1, y1 = -1;
        if (pointdata.length != 2) throw new UnsupportedOperationException();
        try {
            x1 = Integer.parseInt(pointdata[0]);
            y1 = Integer.parseInt(pointdata[1]);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException();
        }


        if (x1 < 0 || y1 < 0) throw new UnsupportedOperationException();
        else {
            this.x = x1;
            this.y = y1;
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
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

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
