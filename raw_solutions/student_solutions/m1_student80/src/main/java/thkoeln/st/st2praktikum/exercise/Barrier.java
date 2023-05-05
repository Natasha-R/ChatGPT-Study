package thkoeln.st.st2praktikum.exercise;

public class Barrier {
    private String barrierPosition;
    private int[][] coordinates = {{-1, -1}, {-1, -1}};


    public int[][] convertBarrierString(String barrierString) {

        barrierPosition = barrierPosition.replaceAll("[()]", "");
        String[] barrierCoordinateString = barrierPosition.split("-");
        for (int i = 0; i < barrierCoordinateString.length; i++) {
            coordinates[i][0] = Integer.parseInt(barrierCoordinateString[i].replaceAll(",.*", ""));         //https://stackoverflow.com/questions/27095087/coordinates-string-to-int
            coordinates[i][1] = Integer.parseInt(barrierCoordinateString[i].replaceAll(".*,", ""));
        }
        return coordinates;
    }

    public Barrier(String barrierPosition) {
        this.barrierPosition = barrierPosition;
        coordinates = this.convertBarrierString(barrierPosition);
    }

    public String getBarrierPosition() {
        return barrierPosition;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }
}
