package thkoeln.st.st2praktikum.exercise.entities;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;


@Setter
@Getter
@AllArgsConstructor
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(String position){
        String[] input = position.replace("(", "").replace(")","").split(",");
        x = Integer.parseInt(input[0]);
        y = Integer.parseInt(input[1]);
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public boolean equals(Coordinates coordinates) {
        return (x == coordinates.x && y == coordinates.y);
    }

}



