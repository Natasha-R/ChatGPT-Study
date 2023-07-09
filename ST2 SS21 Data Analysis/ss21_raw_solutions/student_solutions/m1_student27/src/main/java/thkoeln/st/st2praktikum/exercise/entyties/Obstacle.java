package thkoeln.st.st2praktikum.exercise.entyties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Obstacle {
    protected UUID spaceId;
    protected Point start, end;


}
