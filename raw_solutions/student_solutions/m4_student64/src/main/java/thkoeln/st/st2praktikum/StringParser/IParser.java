package thkoeln.st.st2praktikum.StringParser;

import java.util.regex.MatchResult;

public interface IParser {
    /**
     * Liest einen String ein
     */
    public MatchResult parse(String someInputString);
}
