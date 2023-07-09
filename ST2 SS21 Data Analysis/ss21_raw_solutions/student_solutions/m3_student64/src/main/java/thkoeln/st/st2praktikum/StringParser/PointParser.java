package thkoeln.st.st2praktikum.StringParser;

import thkoeln.st.st2praktikum.StringParser.Exception.InvalidParsingString;

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
            throw new InvalidParsingString();
        }

        return patternMatcher.toMatchResult();
    }
}
