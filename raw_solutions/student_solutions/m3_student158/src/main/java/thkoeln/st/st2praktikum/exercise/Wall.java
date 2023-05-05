package thkoeln.st.st2praktikum.exercise;

import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Wall {

    @Embedded
    @Setter
    private Coordinate start;

    @Embedded
    @Setter
    private Coordinate end;

    public Wall() {
    }

    public Wall(Coordinate pos1, Coordinate pos2) {
        assertWallNotDiagonal(pos1, pos2);
        setCoordinatesInOrder(pos1, pos2);
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String input) {
        if(!input.contains("-")){
            throw new IllegalArgumentException("Wall String not given in expected format '(x,y)-(x,y)'");
        }
        Coordinate first = new Coordinate(input.split("-")[0]);
        Coordinate second = new Coordinate(input.split("-")[1]);
        setCoordinatesInOrder(first, second);
    }

    private void assertWallNotDiagonal(Coordinate first, Coordinate second){
        if(first.getX() != second.getX() && first.getY() != second.getY()){
            throw new IllegalArgumentException("Wall must not be diagonal");
        }
    }

    private void setCoordinatesInOrder(Coordinate first, Coordinate second){
        if(first.getX()<=second.getX() && first.getY()<=second.getY()){
            this.start = first;
            this.end = second;
        } else {
            this.start = second;
            this.end = first;
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public Boolean isVertical(){
        return getStart().getY() == getEnd().getY();
    }

    public Boolean isHorizontal(){
        return getStart().getX() == getEnd().getX();
    }
}
