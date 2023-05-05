package thkoeln.st.st2praktikum.exercise.entyties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Point {


  protected   int x,y;



    @Override
    public String toString() {
        return "(" + x +"," + y +')';
    }
}
