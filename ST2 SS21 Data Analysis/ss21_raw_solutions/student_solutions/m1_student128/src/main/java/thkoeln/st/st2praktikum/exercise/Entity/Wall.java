package thkoeln.st.st2praktikum.exercise.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Wall {
    protected UUID fieldId;
    protected Point start, end;


}
