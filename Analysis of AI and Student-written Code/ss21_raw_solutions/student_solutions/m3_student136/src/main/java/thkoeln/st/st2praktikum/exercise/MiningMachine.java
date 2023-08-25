package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MiningMachine
{
    @Embedded
    public static final Vector2D zeroCoordinate = new Vector2D(0, 0);

    @Id
    private UUID uuid;

    private boolean initialised;


    private String name;

    @OneToOne
    private Field field;
    @Embedded
    private Vector2D coordinate;

    public MiningMachine(String name)
    {
        uuid = UUID.randomUUID();

        initialised = false;


        this.name = name;

        field = null;
        coordinate = new Vector2D(zeroCoordinate.getX(), zeroCoordinate.getY());
    }

    public boolean isInitialised()
    {
        return initialised;
    }
    public void setInitialised(boolean initialised)
    {
        this.initialised = initialised;
    }

    public UUID getFieldId()
    {
        return field.getUuid();
    }

    public Vector2D getVector2D()
    {
        return coordinate;
    }
}