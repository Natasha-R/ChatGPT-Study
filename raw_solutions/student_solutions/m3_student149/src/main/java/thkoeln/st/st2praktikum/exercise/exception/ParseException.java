package thkoeln.st.st2praktikum.exercise.exception;

public class ParseException extends RuntimeException {

    public ParseException(String stringToParse, String pattern) {
        super("String '" + stringToParse + "' could not be parsed with pattern '" + pattern + "'");
    }

}
