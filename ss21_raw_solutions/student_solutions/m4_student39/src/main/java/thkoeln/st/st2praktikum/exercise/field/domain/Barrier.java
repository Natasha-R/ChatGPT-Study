package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Barrier {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "startX")),
            @AttributeOverride(name = "y", column = @Column(name = "startY"))
    })
    private Vector2D start;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "endX")),
            @AttributeOverride(name = "y", column = @Column(name = "endY"))
    })
    private Vector2D end;


    public Barrier(Vector2D start, Vector2D end) {
        setVectors(start, end);

    }

    private Barrier(String barrierString) {
        int i = 5;
        while(barrierString.charAt(i)!='-'){
            if(barrierString.length()<=i+5)
                throw new RuntimeException("Invalid barrier string");
            i++;
        }
        setVectors(new Vector2D(barrierString.substring(0,i)), new Vector2D(barrierString.substring(i+1)));
    }

    private void setVectors(Vector2D pos1, Vector2D pos2){
        if(isHorizontal(pos1, pos2)){
            if(pos1.getX() < pos2.getX()){
                this.start = pos1;
                this.end = pos2;
            }else if(pos2.getX() < pos1.getX()){
                this.start = pos2;
                this.end = pos1;
            }else
                throw new RuntimeException("Start position equal end position");
        }else if(isVertical(pos1, pos2)){
            if(pos1.getY() < pos2.getY()){
                this.start = pos1;
                this.end = pos2;
            }else if(pos2.getY() < pos1.getY()){
                this.start = pos2;
                this.end = pos1;
            }else
                throw new RuntimeException("Start position equal end position");
        }else
            throw new RuntimeException("Barrier is not vertical or horizontal");
    }

    public static boolean isVertical(Vector2D vector1, Vector2D vector2){
        return vector1.getX().equals(vector2.getX());
    }

    public static boolean isHorizontal(Vector2D vector1, Vector2D vector2){
        return vector1.getY().equals(vector2.getY());
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public boolean xIsBetween(Integer x){
        if(x >= start.getX() && x < end.getX())
            return true;
        else return x < start.getX() && x >= end.getX();
    }
    public boolean yIsBetween(Integer y){
        if(y >= start.getY() && y < end.getY())
            return true;
        else return y < start.getY() && y >= end.getY();
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        return new Barrier(barrierString);
    }

}
