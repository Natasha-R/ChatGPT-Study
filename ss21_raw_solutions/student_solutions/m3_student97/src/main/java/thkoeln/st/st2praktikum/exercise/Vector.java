package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Access(AccessType.FIELD)
public class Vector {

    private Integer x;
    private Integer y;


    public Vector(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX () {
        return this.x;
    }

    public Integer getY () {
        return this.y;
    }
}
