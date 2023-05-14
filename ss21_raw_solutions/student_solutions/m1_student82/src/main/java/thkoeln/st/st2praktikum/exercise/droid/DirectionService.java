package thkoeln.st.st2praktikum.exercise.droid;

public interface DirectionService {
    static Direction stringToDirection(String directionString) throws IllegalArgumentException{

        switch (directionString){
            case "no":
                return Direction.NO;

            case "so":
                return Direction.SO;

            case"ea":
                return Direction.EA;

            case"we":
                return  Direction.WE;

            case"tr":
                return Direction.TR;

            case"en":
                return Direction.EN;
            default:
                throw new IllegalArgumentException(directionString);
        }


    }
}
