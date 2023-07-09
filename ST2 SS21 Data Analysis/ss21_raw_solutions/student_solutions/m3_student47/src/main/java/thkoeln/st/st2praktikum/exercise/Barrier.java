package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Barrier {
    @Embedded
    private Point start;
    @Embedded
    private Point end;

    public Barrier(Point pos1, Point pos2) {
        this.start = pos1;
        this.end = pos2;

        if(start.getX() > end.getX() || start.getY() > end.getY()){
            this.start = pos2;
            this.end = pos1;
        }

        if(start.getX() < 0 || start.getY() <0 || end.getX() < 0 || end.getY() <0) throw new RuntimeException("negativ numbers");
        if(!start.getX().equals(end.getX()) && !end.getY().equals(start.getY())) throw new RuntimeException("no diagonals");
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        String[] input = barrierString.split("-");
        input[0] = removeFirstAndLastCharacter(input[0]);
        input[1]= removeFirstAndLastCharacter(input[1]);

        if((!input[0].matches("\\d+[,]+\\d")) || !input[1].matches("\\d+[,]+\\d")) throw new RuntimeException("wrong format");

        String[] barrierstart = input[0].split(",");
        String[] barrierend = input[1].split(",");

        this.start = new Point(Integer.parseInt(barrierstart[0]),Integer.parseInt(barrierstart[1]));
        this.end = new Point(Integer.parseInt(barrierend[0]),Integer.parseInt(barrierend[1]));
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