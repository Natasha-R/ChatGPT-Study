package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@Embeddable
public class Barrier {

    @Embedded
    private Point start;
    @Embedded
    private Point end;


    public Barrier(Point start, Point end) {
        
        this.start = start;
        this.end = end;

        if(start.getX() > end.getX() || start.getY() > end.getY()){
            this.start = end;
            this.end = start;
        }

        if(start.getX() < 0 || start.getY() <0 || end.getX() < 0 || end.getY() <0) throw new RuntimeException("negativ numbers");
        if(!start.getX().equals(end.getX()) && !end.getY().equals(start.getY())) throw new RuntimeException("no diagonals");
        
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        Barrier barrier = new Barrier(new Point(0,0),new Point(0,0));
        String[] input = barrierString.split("-");
        input[0] = barrier.removeFirstAndLastCharacter(input[0]);
        input[1]= barrier.removeFirstAndLastCharacter(input[1]);

        if((!input[0].matches("\\d+[,]+\\d")) || !input[1].matches("\\d+[,]+\\d")) throw new RuntimeException("wrong format");

        String[] barrierstart = input[0].split(",");
        String[] barrierend = input[1].split(",");

        barrier.start = new Point(Integer.parseInt(barrierstart[0]),Integer.parseInt(barrierstart[1]));
        barrier.end = new Point(Integer.parseInt(barrierend[0]),Integer.parseInt(barrierend[1]));
        return barrier;
    }

    public String removeFirstAndLastCharacter(String S){
        StringBuilder s = new StringBuilder(S);
        s.deleteCharAt(0);
        s.deleteCharAt(s.length()-1);
        return s.toString();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
    
}
