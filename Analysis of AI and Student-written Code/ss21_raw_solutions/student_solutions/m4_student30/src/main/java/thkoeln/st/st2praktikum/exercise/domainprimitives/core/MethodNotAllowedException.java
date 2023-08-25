package thkoeln.st.st2praktikum.exercise.domainprimitives.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "Entity processing went wrong")
public class MethodNotAllowedException extends RuntimeException {
    private Exception cause;
}

