package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Wall {

    private Coordinate start;
    private Coordinate end;

    public Wall(Coordinate position1, Coordinate position2) {
        if(position1.getX().equals(position2.getX()) || position1.getY().equals(position2.getY())) {
            if (position1.getX()+position1.getY() > position2.getX()+position2.getY()) {
                this.start = position2;
                this.end = position1;
            }else {
                this.start = position1;
                this.end = position2;
            }
        }else throw new RuntimeException();
    }

    private static boolean isTheStringCorrect(String wall){

        if (wall.length() >= 11){
            if (wall.replaceAll("[0-9]","").equals("(,)-(,)") && wall.replaceAll("[^0-9]","").length()>=4) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param stringForWall the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String stringForWall) {

        Integer startOfX = Integer.parseInt(String.valueOf(stringForWall.charAt(1)));
        Integer startOfY = Integer.parseInt(String.valueOf(stringForWall.charAt(3)));
        Integer endOfX = Integer.parseInt(String.valueOf(stringForWall.charAt(7)));
        Integer endOfY = Integer.parseInt(String.valueOf(stringForWall.charAt(9)));

        if (isTheStringCorrect( stringForWall )){
            if (startOfX.equals(endOfX) || startOfY.equals(endOfY)) {
                if((startOfX+startOfY < endOfX+endOfY)) {
                    return new Wall(new Coordinate(startOfX,startOfY),new Coordinate(endOfX,endOfY));
                }else {
                    return new Wall(new Coordinate(endOfX,endOfY),new Coordinate(startOfX,startOfY));
                }
            }else throw new RuntimeException();

        }else  throw new RuntimeException();
    }

    public Coordinate getStart() { return start; }
    public Coordinate getEnd() { return end; }
}