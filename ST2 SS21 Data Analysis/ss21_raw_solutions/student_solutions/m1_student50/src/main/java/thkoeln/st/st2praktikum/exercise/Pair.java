package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

class Pair {

    protected Integer x;
    protected Integer y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x.equals(pair.x) &&
                y.equals(pair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    Pair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX(){ return x; }
    public Integer getY(){ return y; }

    public void setX(Integer x){
        this.x = x;
    }
    public void setY(Integer y){
        this.y = y;
    }
}

