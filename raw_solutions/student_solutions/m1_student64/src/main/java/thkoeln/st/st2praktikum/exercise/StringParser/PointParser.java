package thkoeln.st.st2praktikum.exercise.StringParser;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PointParser implements IParser {
    private final String stringRegex = "\\(([0-9]+),([0-9]+)\\)";
    private final Pattern stringPattern = Pattern.compile(this.stringRegex);

    @Override
    public MatchResult parse(String someInputString) {
        // parse String
        Matcher patternMatcher = this.stringPattern.matcher(someInputString);

        if(!patternMatcher.matches())
        {
            throw new RuntimeException();
        }

        return patternMatcher.toMatchResult();
    }
}
