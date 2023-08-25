package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Barrier
{
    private Vector2D start;
    private Vector2D end;


    public Barrier(Vector2D one, Vector2D two)
    {
        // check for whether the barrier is horizontal or vertical
        if(!(one.getX() == two.getX() || one.getY() == two.getY()))
        {
            throw new BarrierException();
        }

        // sort the Vector2Ds so that the first one is the one closer to the bottom left corner
        if(one.getX() < two.getX() || one.getY() < two.getY())
        {
            this.start = one;
            this.end   = two;
        }
        else
        {
            this.start = two;
            this.end   = one;
        }
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString)
    {
        String[] vector2DStrings = barrierString.split("-");

        Vector2D one = new Vector2D(vector2DStrings[0]);
        Vector2D two = new Vector2D(vector2DStrings[1]);


        // check whether the string is formatted correctly
        if (vector2DStrings.length != 2)
        {
            throw new BarrierException();
        }

        // check for whether the barrier is horizontal or vertical
        if(!(one.getX() == two.getX() || one.getY() == two.getY()))
        {
            throw new BarrierException();
        }

        // sort the Vector2Ds so that the first one is the one closer to the bottom left corner
        if(one.getX() < two.getX() || one.getY() < two.getY())
        {
            this.start = one;
            this.end   = two;
        }
        else
        {
            this.start = two;
            this.end   = one;
        }
    }
    
    public Vector2D getStart()
    {
        return start;
    }

    public Vector2D getEnd()
    {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString)
    {
        return new Barrier(barrierString);
    }
    
}
