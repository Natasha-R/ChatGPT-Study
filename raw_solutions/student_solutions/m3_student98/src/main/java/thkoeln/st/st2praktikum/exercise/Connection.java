package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connection {

    @Id
    @Getter
    private UUID id;
    @Getter
    @OneToOne (cascade = CascadeType.ALL)
    private Vector2D fromPosition;
    @Getter
    @OneToOne (cascade = CascadeType.ALL)
    private Vector2D toPosition;
    @Getter
    @ManyToOne
    private TransportCategory transportCategory;


    public Connection(Vector2D fromPosition, Vector2D toPosition, TransportCategory transportCategory) {
        this.id = UUID.randomUUID();
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
        this.transportCategory = transportCategory;
    }
}
