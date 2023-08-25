package thkoeln.archilab.bauzeichner20.solution.canvas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Setter
@Getter
public abstract class Drawable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    public abstract void setCanvas( Canvas canvas );

    public abstract Integer xBottomLeft();
    public abstract void setXBottomLeft( Integer xBottomLeft );
    public abstract Integer yBottomLeft();

    public abstract Integer width();
    public abstract Integer height();


}
