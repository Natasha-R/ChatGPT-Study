package thkoeln.st.st2praktikum.exercise;

public class Wall {
    String wallposition;
    int coordinates[][] = {{-1, -1}, {-1, -1}};

    public int[][] transformWallString(String wallposition) {

        wallposition = wallposition.replaceAll("[()]", "");
        String[] wallcoordinatestring = wallposition.split("-");
        for(int i=0 ; i<wallcoordinatestring.length; i++) {
            coordinates[i][0] = Integer.parseInt(
                    wallcoordinatestring[i].replaceAll(",.*", ""));
            coordinates[i][1] = Integer.parseInt(
                    wallcoordinatestring[i].replaceAll(".*,", ""));
        }
        return coordinates;
    }


    public Wall(String wallposition) {
        this.wallposition = wallposition;
        coordinates = this.transformWallString(wallposition);


    }
}
