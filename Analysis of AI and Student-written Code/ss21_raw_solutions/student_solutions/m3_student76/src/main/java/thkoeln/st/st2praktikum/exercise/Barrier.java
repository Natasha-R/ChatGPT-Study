package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Barrier {
    @Embedded
    private Point start;
    @Embedded
    private Point end;


    public Barrier(Point pos1, Point pos2) {
        if((pos1.getX().equals(pos2.getX())|| pos1.getY().equals(pos2.getY()))){
            if(pos1.getX()+ pos1.getY() > pos2.getX()+ pos2.getY()){
                this.start = pos2;
                this.end = pos1;
            }else{
                this.start = pos1;
                this.end= pos2;
            }
        } else throw  new RuntimeException();
    }

    public boolean checkBarrierString(String barrier){
        if(barrier.length()>= 11){
            return  barrier.replaceAll("[0-9]","").equals("(,)-(,)")
                    && barrier.replaceAll("[^0-9]", "").length() >= 4;
        }else return false;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        Integer xStart = Integer.parseInt(String.valueOf(barrierString.charAt(1)));
        Integer xEnd = Integer.parseInt(String.valueOf(barrierString.charAt(7)));
        Integer yStart = Integer.parseInt(String.valueOf(barrierString.charAt(3)));
        Integer yEnd = Integer.parseInt(String.valueOf(barrierString.charAt(9)));

        if(checkBarrierString(barrierString)){
            if(xStart.equals(xEnd) || yStart.equals(yEnd)){
                if(xStart + yStart < xEnd + yEnd){
                    this.start = new Point(xStart,yStart);
                    this.end= new Point(xEnd,yEnd);
                }else{
                    this.start = new Point(xEnd,yEnd);
                    this.end = new Point(xStart,yStart);
                }

            } else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
