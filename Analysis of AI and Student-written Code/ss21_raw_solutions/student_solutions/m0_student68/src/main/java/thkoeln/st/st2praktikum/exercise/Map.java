package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Map {
    private Integer xSize;
    private Integer ySize;
    private List<Barrier> barriers;


//    public Map (Integer xSize,Integer ySize, List<Barrier> barriers) {
//        this.xSize = xSize;
//        this.ySize = ySize;
//        this.barriers = barriers;
//    }

}
