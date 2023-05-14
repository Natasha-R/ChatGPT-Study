package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Point {

    protected Integer x;
    protected Integer y;

    @Override
    public boolean equals(Object o ){
        if(this == o) return  true;
        if(o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o ;
        return  x.equals(point.x) && y.equals(point .y);
    }

        @Override
        public int hashCode() {
            return  Objects.hash(x,y);
        }

        Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        /*public Integer getX(){ return x; }
        public Integer getY(){ return y; }

        public void setX(Integer x){
            this.x = x;
        }
        public void setY(Integer y){
            this.y = y;*/
}



