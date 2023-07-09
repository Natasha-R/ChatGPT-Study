package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
public class Field
{
    public boolean fieldOccupied;

    public Field() { }
}
