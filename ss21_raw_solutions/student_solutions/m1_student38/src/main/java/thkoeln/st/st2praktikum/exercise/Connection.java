package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class Connection{
    int sourceX;
    int sourceY;
    int destX;
    int destY;
    UUID sourceField;
    UUID destField;

    public Connection(UUID sourceField, UUID destField, String sourceCoords, String destCoords){
        this.sourceField = sourceField;
        this.destField = destField;
        this.determineCoordsFromStrings(sourceCoords,destCoords);
    }

    void determineCoordsFromStrings(String sourceCoords, String destCoords){
        String[] sourceCoords_Split = sourceCoords.replaceAll("[()]", "").split(",");
        String[] destCoords_Split = destCoords.replaceAll("[()]", "").split(",");
        this.sourceX = Integer.parseInt(sourceCoords_Split[0]);
        this.sourceY = Integer.parseInt(sourceCoords_Split[1]);
        this.destX = Integer.parseInt(destCoords_Split[0]);
        this.destY = Integer.parseInt(destCoords_Split[1]);
    }
}