package thkoeln.st.st2praktikum.exercise.StringParser;

import java.util.regex.MatchResult;

public interface IParser {
    public MatchResult parse(String someInputString);
}
