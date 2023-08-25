package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if(x<0 || y<0)
            throw new RuntimeException();
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if(!checkVectorString(vector2DString)){
            throw new RuntimeException("Invalid vector string");
        }
        List<Integer> listOfNumbers = decodeNumbers(vector2DString);
        x = listOfNumbers.get(0);
        y = listOfNumbers.get(1);
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

    public static boolean checkVectorString(String vectorString){
        int numberOfCommas = 0;
        if(vectorString.charAt(0)=='(' && vectorString.charAt(vectorString.length()-1)==')'){
            for(int i=1;i<vectorString.length()-1;i++){
                if(vectorString.charAt(i)==',')
                    numberOfCommas++;
                else if(!Character.isDigit(vectorString.charAt(i)))
                    return false;
            }
            return numberOfCommas == 1;
        }
        else
            return false;
    }
    public static List<Integer> decodeNumbers(String command) {
        List<Integer> listOfInt = new ArrayList<>();
        boolean foundNumber = false;
        int decodedNumber = 0;
        for (int i = 0; i < command.length(); i++) {
            if (Character.isDigit(command.charAt(i))) {
                decodedNumber = decodedNumber * 10 + Character.getNumericValue(command.charAt(i));
                foundNumber = true;
            } else if (foundNumber) {
                listOfInt.add(decodedNumber);
                decodedNumber = 0;
                foundNumber = false;
            }
        }
        return listOfInt;
    }
}
