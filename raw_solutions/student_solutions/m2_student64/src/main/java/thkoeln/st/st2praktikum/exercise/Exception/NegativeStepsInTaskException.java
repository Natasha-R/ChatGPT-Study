package thkoeln.st.st2praktikum.exercise.Exception;

public class NegativeStepsInTaskException extends RuntimeException {
    public NegativeStepsInTaskException() {
        super("Task darf keine negativen Schritte enthalten. Zu diesem Zweck wird explizit die Richtung angegeben");
    }
}
