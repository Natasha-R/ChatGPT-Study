package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
public class TransportCategory {

    @Id
    @Getter
    private UUID id;
    @Getter
    private String type;


    public TransportCategory (String type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }
}