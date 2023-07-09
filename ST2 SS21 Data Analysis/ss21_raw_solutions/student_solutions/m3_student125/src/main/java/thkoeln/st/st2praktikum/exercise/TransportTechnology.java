package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TransportTechnology
{
    private String name;

    public TransportTechnology(String name)
    {
        this.setName(name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
