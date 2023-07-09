package thkoeln.st.st2praktikum.exercise.transportcategory.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class TransportCategory {
    @Id
    UUID transportCategoryId;
    String category;
    @OneToMany(cascade = CascadeType.REMOVE)
    private final List<Connection> connections = new ArrayList<>();

    public TransportCategory(String category) {
        this.category = category;
        this.transportCategoryId=UUID.randomUUID();
    }
}
