package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    @Setter
    @Getter
    int x,y;




    @Override
    public String toString() {
        return "(" + x +"," + y +')';
    }
}
