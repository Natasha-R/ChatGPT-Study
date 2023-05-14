package thkoeln.st.st2praktikum.exercise;

public class Barrier implements DeStringable {
    private int x1, y1, x2, y2;

    public Barrier(int x1, int y1, int x2, int y2) {
        this.setX1(x1);
        this.setY1(y1);
        this.setX2(x2);
        this.setY2(y2);
    }

    public Barrier() {
    }

    public int[] deString(String barrierString) {
        String barrierUnStrung = barrierString.replace("(", "");
        barrierUnStrung = barrierUnStrung.replace(")", "");
        barrierUnStrung = barrierUnStrung.replace("-", ",");
        String[] barrierArray = barrierUnStrung.split(",");
        int[] pointArray = new int[barrierArray.length];
        for (int i = 0; i < barrierArray.length; i++) {
            pointArray[i] = Integer.parseInt(barrierArray[i].trim());
        }
        return (pointArray);
    }

    public int getBarrierXStart() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getBarrierYStart() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getBarrierXEnd() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getBarrierYEnd() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
}