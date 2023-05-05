package thkoeln.st.st2praktikum.exercise.converter;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.OrderType;

import java.util.UUID;

@NoArgsConstructor
public class StringConverter implements Convertable {

    @Override
    public int[] pointStringToIntArray( String pointString ){
        checkPointString( pointString );

        pointString = removeBrackets(pointString);
        int[] ints = new int[2];
        ints[0] = Integer.parseInt(pointString.replaceAll(",.*", ""));         //https://stackoverflow.com/questions/27095087/coordinates-string-to-int
        ints[1] = Integer.parseInt(pointString.replaceAll(".*,", ""));
        return ints;
    }

    @Override
    public UUID toUUID( String input ) {
        return UUID.fromString( toUUIDAsString ( input ) );
    }

    public String toUUIDAsString( String input ) {
        input = removeBrackets( input );
        return input.split("," )[1];
    }

    public String[] splitBarrierString( String barrierString ) {
        return barrierString.split("-");
    }

    public String removeBrackets( String input ) {
        String output = input.replaceAll("\\[", "" ).replaceAll("]", "" );
        output = output.replaceAll( "\\(", "" ).replaceAll( "\\)", "" );
        return output;
    }

    public OrderType toOrderType(String orderString) {
        String command = toCommand(orderString);
        switch ( command ){
            case ("en"):
                return OrderType.ENTER;
            case ("tr"):
                return OrderType.TRANSPORT;
            case ("no"):
                return OrderType.NORTH;
            case ("ea"):
                return OrderType.EAST;
            case ("so"):
                return OrderType.SOUTH;
            case ("we"):
                return OrderType.WEST;
            default:
                throw new InvalidStringException(orderString + " <- This does not contain a valid OrderType" );
        }
    }

    public String toCommand(String input) {
        return removeBrackets(input).split(",")[0];
    }


    public int toSteps(String input){
        input = removeBrackets(input);
        return Integer.parseInt(input.split(",")[1]);
    }

    private boolean isOrder(String input ) {
        for ( OrderType type : OrderType.values()) {
            if ( type.name().toLowerCase().contains(input)) return true;
        }
        return false;
    }

    private String getFirstPartOfString(String input) {
        return removeBrackets(input).split(",")[0];
    }

    private boolean checkPointString(String pointString) {
        char[] chars = { '(' , ',' , ')' };
        for ( int i = 0; i < chars.length ; i++)
            if ( countCharInString( pointString , chars[i] ) != 1 || pointString.contains("-")) {
                throw new InvalidStringException( pointString + " <- This is no valid String");
        }
        return true;
    }

    private int countCharInString( String s, char c ) {
        int count = 0;
        for ( int i = 0; i < s.length(); i++ ) {
            if ( s.charAt( i ) == c ) {
                count++;
            }
        }
        return count;
    }


}
