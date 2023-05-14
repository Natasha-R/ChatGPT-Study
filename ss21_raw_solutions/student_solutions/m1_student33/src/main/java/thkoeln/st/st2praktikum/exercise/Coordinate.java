package thkoeln.st.st2praktikum.exercise;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    int x;
    int y;

    public static Coordinate switchStringToCoordinate (String coordinate){
        String[] coordinateA = coordinate.replace("(" ,  "").replace(")", "").split(",");
        Coordinate coordinateNew = new Coordinate();
        coordinateNew.x = Integer.parseInt(coordinateA[0]);
        coordinateNew.y = Integer.parseInt(coordinateA[1]);
        return coordinateNew;
    }

    public String getString(){
        return ("(" + x +  "," + y + ")");
    }



}
