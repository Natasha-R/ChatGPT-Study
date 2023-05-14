package thkoeln.st.st2praktikum.exercise;

public class CoordianteGetterFromString {

    public int getXfromString(String coordinates){
        String segments[] = coordinates.split(",");
        return Integer.parseInt(segments[0].replace("("," ").trim());
    }

    public int getYfromString(String coordinates){
        String segments[] = coordinates.split(",");
        return Integer.parseInt(segments[1].replace(")"," ").trim());
    }
}
