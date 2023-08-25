package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private BarrierType barrierType;

    public Barrier(String barrierString) {
        String[] coords = barrierString.split("-");
        this.startX = Integer.parseInt(coords[0].substring(1, 2));
        this.startY = Integer.parseInt(coords[0].substring(3, 4));
        this.endX = Integer.parseInt(coords[1].substring(1, 2));
        this.endY = Integer.parseInt(coords[1].substring(3, 4));
        if (startX > endX) {
            int tmp = startX;
            startX = endX;
            endX = tmp;
        }
        if (startY > endY) {
            int tmp = startY;
            startY = endY;
            endY = tmp;
        }
        if (startX == endX) {
            this.barrierType = BarrierType.VERTICAL;
        } else {
            this.barrierType = BarrierType.HORIZONTAL;
        }
    }

    public boolean willHitBarrier(int currentX, int currentY, String direction) {
        switch (direction) {
            case "ea":
                return willHitBarrierWhenMovingHorizontally(currentX, currentY, currentX + 1);
            case "we":
                return willHitBarrierWhenMovingHorizontally(currentX, currentY, currentX - 1);
            case "no":
                return willHitBarrierWhenMovingVertically(currentX, currentY,  currentY + 1);
            case "so":
                return willHitBarrierWhenMovingVertically(currentX, currentY,  currentY - 1);
            default:
                throw new IllegalArgumentException("Invalid direction String");
        }
    }

    /**
     * Checks whether the MiningMachine will hit this barrier when moving north or south
     * @param currentX current X position of the MiningMachine
     * @param currentY current Y position of the MiningMachine
     * @param nextY next Y position of the MiningMachine
     * @return true if it will hit the Barrier, otherwise false
     */
    private boolean willHitBarrierWhenMovingVertically(int currentX, int currentY, int nextY) {
        if (barrierType.isVertical()) return false;

        if (currentX < startX || currentX >= endX) return false;

        return (nextY < startY && currentY >= startY) || (nextY >= startY && currentY < startY);
    }

    /**
     * Checks whether the MiningMachine will hit this barrier when moving east or west
     * @param currentX current X position of the MiningMachine
     * @param currentY current Y position of the MiningMachine
     * @param nextX next X position of the MiningMachine
     * @return true if it will hit the Barrier, otherwise false
     */
    private boolean willHitBarrierWhenMovingHorizontally(int currentX, int currentY, int nextX) {
        if (barrierType.isHorizontal()) return false;

        if (currentY < startY || currentY >= endY) return false;

        return (nextX < startX && currentX >= startX) || (nextX >= startX && currentX < startX);
    }
}