package thkoeln.st.st2praktikum.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity for given ID was not found")
public class EntityNotFoundException extends RuntimeException {
    private UUID id;
}

