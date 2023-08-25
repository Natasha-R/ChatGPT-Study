package thkoeln.st.st2praktikum.exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class MiningField {
    private Integer x_size;
    private Integer y_size;
    private List<Walls> walls;
}
