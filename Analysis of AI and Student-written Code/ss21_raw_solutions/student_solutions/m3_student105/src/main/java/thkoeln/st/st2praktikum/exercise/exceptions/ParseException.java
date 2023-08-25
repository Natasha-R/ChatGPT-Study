package thkoeln.st.st2praktikum.exercise.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ParseException extends RuntimeException {
    public String message;
}
