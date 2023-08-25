package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Obstacle {

    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        if(     !(pos1.getX() >= 0 &&
                pos1.getY() >= 0 &&
                pos2.getX() >= 0 &&
                pos2.getY() >= 0)
        ) throw new RuntimeException("Coordinate was negative");

        if(pos1.shareXAxis(pos2) || pos1.shareYAxis(pos2) ){
            if(pos1.getX() > pos2.getX()){
                this.start = pos2;
                this.end = pos1;
            }else if (pos1.getY() > pos2.getY()){
                this.start = pos2;
                this.end = pos1;
            }else {
                this.start = pos1;
                this.end = pos2;
            }
        }else throw new RuntimeException("Walls are Diagonal");
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        String[] coordinates = obstacleString.split("-");

        if(     coordinates[0].contains("(") && coordinates[0].contains(")") &&
                coordinates[1].contains("(") && coordinates[1].contains(")")
        ) {


            Coordinate pos1 = new Coordinate(coordinates[0]);
            Coordinate pos2 = new Coordinate(coordinates[1]);


            if (pos1.shareXAxis(pos2) || pos1.shareYAxis(pos2)) {
                if (pos1.getX() > pos2.getX()) {
                    this.start = pos2;
                    this.end = pos1;
                } else if (pos1.getY() > pos2.getY()) {
                    this.start = pos2;
                    this.end = pos1;
                } else {
                    this.start = pos1;
                    this.end = pos2;
                }
            } else throw new RuntimeException("Walls are Diagonal");
        } else throw new RuntimeException("Invalid Command");

        //throw new UnsupportedOperationException();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public boolean isHorizontal(){
        return this.start.getY() == this.end.getY() && this.end.getX() - this.start.getX() != 0;
    }
    public boolean isVertical(){
        return this.start.getX() == this.end.getX() && this.end.getY() - this.start.getY() != 0;
    }
    public boolean inYrange(Coordinate coordinate){
        return coordinate.getY() >= this.start.getY() && coordinate.getY() < this.end.getY();
    }

    public boolean inXrange(Coordinate coordinate){
        return coordinate.getX() >= this.start.getX() && coordinate.getX() < this.end.getX();
    }

    @Override
    public String toString(){
        return this.start.toString()+"-"+this.end.toString();
    }



}
