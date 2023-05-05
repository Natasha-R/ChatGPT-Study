package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.exception.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Parsable<T> {

    default T parse(String string) throws ParseException {
        return create(getMatcher(string));
    }

    default Matcher getMatcher(String string) throws ParseException {
        if (string == null) {
            throw new ParseException("null", getPattern());
        }
        Pattern pattern = Pattern.compile(getPattern());
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            return matcher;
        } else {
            throw new ParseException(string, getPattern());
        }

    }

    T create(Matcher matcher) throws ParseException;

    String getPattern();
}
