package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

@Getter
public enum BarrierType {
    HORIZONTAL(true, false), VERTICAL(false, true);

    private final boolean isHorizontal;
    private final boolean isVertical;

    BarrierType(boolean isHorizontal, boolean isVertical){
        this.isHorizontal = isHorizontal;
        this.isVertical = isVertical;
    }

}
