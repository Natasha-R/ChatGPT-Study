package thkoeln.st.st2praktikum.exercise.Entities;

public class CoordinateStringReader {
    public Coordinates readCoordinatesOfString(String cordinatenString){
        cordinatenString = cordinatenString.replace("(","");
        cordinatenString = cordinatenString.replace(")","");
        String [] splittedCorrdinatenString = cordinatenString.split(",");
        Coordinates xyCoordinates = new Coordinates(Integer.parseInt(splittedCorrdinatenString[0]),Integer.parseInt(splittedCorrdinatenString[1]));
        return xyCoordinates  ;
    }
    public String [] splittStringWithTwoCoordinates(String twoCoordinateString){
        return twoCoordinateString.split("-");
    }
}
