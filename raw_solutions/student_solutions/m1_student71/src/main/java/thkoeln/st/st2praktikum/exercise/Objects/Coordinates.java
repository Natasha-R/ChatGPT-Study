package thkoeln.st.st2praktikum.exercise.Objects;

import javax.management.InvalidAttributeValueException;
import java.util.Objects;

public class Coordinates {

    public int X;
    public int Y;

    public Coordinates(int x, int y){
        X = x;
        Y = y;
    }

    //Syntax "(0,3)"
    public Coordinates(String cordString) throws InvalidAttributeValueException {
        set(cordString);
    }

    //Syntax "(0,3)"
    public void set(String cordString) throws InvalidAttributeValueException {
        if(cordString.toCharArray()[0] != '(' || cordString.toCharArray()[cordString.length()-1] != ')')
            throw new InvalidAttributeValueException("...");

        var rawString = cordString.substring(1 , cordString.length()-1);
        X = Integer.parseInt(rawString.split(",")[0]);
        Y = Integer.parseInt(rawString.split(",")[1]);
    }
    public void set(int x,int y){
        X = x;
        Y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)",X,Y);
    }

    public boolean equals(Coordinates o) {
        return this.X == o.X && this.Y == o.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }
}
