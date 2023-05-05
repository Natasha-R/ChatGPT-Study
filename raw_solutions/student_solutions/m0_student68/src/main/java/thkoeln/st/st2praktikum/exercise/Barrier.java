package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Barrier {
    private Integer xStart;
    private Integer yStart;
    private Integer xEnd;
    private Integer yEnd;

    public Barrier (Integer xStart,Integer yStart, Integer xEnd, Integer yEnd){
        this.xStart=xStart;
        this.yStart=yStart;
        this.xEnd=xEnd;
        this.yEnd=yEnd;
    }

}
