package thkoeln.st.st2praktikum.exercise;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Setter
@NoArgsConstructor
@Embeddable
public class Point {

    @Column (insertable = false, updatable = false)
    private Integer x;
    @Column (insertable = false, updatable = false)
    private Integer y;


    public Point(Integer x, Integer y) {
        if(x>= 0 && y>= 0){
        this.x = x;
        this.y = y;
        } else throw new RuntimeException();
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        int x = Integer.parseInt(String.valueOf(pointString.charAt(1)));
        int y = Integer.parseInt(String.valueOf(pointString.charAt(3)));

        if(pointString.length()>=5 &&
        pointString.replaceAll("[0-9]","").equals("(,)")&& pointString.replaceAll("[^0-9]","").length()==2){
            if(x>= 0 && y>= 0){
                this.x = x;
                this.y = y;
            } else throw new RuntimeException();
        }else throw new RuntimeException();

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
}
