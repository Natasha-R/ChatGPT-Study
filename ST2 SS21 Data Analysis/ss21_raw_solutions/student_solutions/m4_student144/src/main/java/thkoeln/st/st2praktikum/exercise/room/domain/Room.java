package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room{

    private Integer height;
    private Integer width;
    @Id
    private UUID roomId;

    public Room(Integer roomHeight, Integer roomWidth){
        width = roomWidth;
        height = roomHeight;
        roomId = UUID.randomUUID();

    }
}
