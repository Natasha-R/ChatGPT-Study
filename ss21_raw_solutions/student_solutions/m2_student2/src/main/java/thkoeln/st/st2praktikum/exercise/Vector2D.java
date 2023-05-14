package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;
    private Boolean isValid = false;


    public Vector2D(Integer x, Integer y) {
        if(x >=0 && y >= 0) {
            this.x = x;
            this.y = y;
        }
        else
            throw new VectorException();
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if(checkVector(vector2DString)) {
            this.isValid = true;
            this.x = Integer.parseInt(vector2DString.replace("(", "").replaceAll(",.*", ""));
            this.y = Integer.parseInt(vector2DString.replaceAll(".*,", "").replace(")", ""));
        }
        else
            throw new VectorException();
    }

    private Boolean checkVector(String vector2DString){
        Integer x = Integer.parseInt(vector2DString.replace("(","").replaceAll(",.*",""));
        Integer y = Integer.parseInt(vector2DString.replaceAll(".*,","").replace(")",""));

        long countComma = vector2DString.chars().filter(ch -> ch == ',').count();

        if(vector2DString.charAt(0) != '(' || vector2DString.charAt(vector2DString.length()-1) != ')') return false;
        if(countComma != 1) return false;
        if(!Character.isDigit(vector2DString.charAt(1)) || !Character.isDigit(vector2DString.charAt(vector2DString.length()-2))) return false;
        if(x != null && y != null){
            if(x < 0 || y < 0) return false;
        }

        return true;
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

    public Boolean getIsValid(){
        return isValid;
    }
}
