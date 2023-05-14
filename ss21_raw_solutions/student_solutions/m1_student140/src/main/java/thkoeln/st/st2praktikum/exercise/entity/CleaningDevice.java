package thkoeln.st.st2praktikum.exercise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CleaningDevice {
    @Id
    private UUID cleaningDeviceId;
    private String name;
    private Integer locationX;
    private Integer locationY;

    @ManyToOne
    @JoinColumn(name = "Space_ID")
    private Space space;

    public CleaningDevice(UUID cleaningDeviceId, String name) {
        this.cleaningDeviceId = cleaningDeviceId;
        this.name = name;
    }

    public Integer getLocationX() {
        return this.locationX;
    }

    public Integer getLocationY() {
        return this.locationY;
    }

    public UUID getCleaningDeviceId() {
        return this.cleaningDeviceId;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    public Space getSpace(){
        return this.space;
    }

}
