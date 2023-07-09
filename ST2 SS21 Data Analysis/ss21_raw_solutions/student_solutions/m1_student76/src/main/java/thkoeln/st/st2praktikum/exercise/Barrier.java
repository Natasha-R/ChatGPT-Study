package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

@Getter
public class Barrier {

    private Integer startX;
    private Integer endX;
    private Integer startY;
    private Integer endY;

    public Barrier(String barrierString){

        int startX= Integer.parseInt(String.valueOf(barrierString.charAt(1)));
        int endX= Integer.parseInt(String.valueOf(barrierString.charAt(7)));
        int startY= Integer.parseInt(String.valueOf(barrierString.charAt(3)));
        int endY= Integer.parseInt(String.valueOf(barrierString.charAt(9)));

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }


}
