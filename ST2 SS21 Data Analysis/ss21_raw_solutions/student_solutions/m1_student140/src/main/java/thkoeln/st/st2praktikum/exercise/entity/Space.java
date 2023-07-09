package thkoeln.st.st2praktikum.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Space {

    @Id
    private UUID spaceId;
    private Integer height;
    private Integer width;


    @OneToMany(
            mappedBy = "space",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Obstacle> obstacles = new ArrayList<>();

    @OneToMany(
            mappedBy = "space",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CleaningDevice> cleaningDevices = new ArrayList<>();



    public Space(UUID spaceId, Integer height, Integer width) {
        this.spaceId = spaceId;
        this.height = height;
        this.width = width;
    }

    public List<Obstacle> getObstacles() { return this.obstacles; }

    public List<CleaningDevice> getCleaningDevices() { return this.cleaningDevices; }

    public UUID getSpaceId() { return this.spaceId; }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWidth() {
        return this.width;
    }
}
