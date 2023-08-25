package thkoeln.st.st2praktikum.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
@JsonIgnoreProperties
public class AbstractEntity {

    @Id
    @JsonIgnore
    protected UUID id;

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    protected AbstractEntity() {
        this.id = UUID.randomUUID();
    }
}
