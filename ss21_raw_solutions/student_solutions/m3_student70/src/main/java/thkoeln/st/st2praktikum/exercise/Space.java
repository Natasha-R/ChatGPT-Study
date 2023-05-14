package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Assist.AbstractEntity;

import javax.persistence.*;
import java.util.*;

    @Entity
    @NoArgsConstructor
    public class Space extends AbstractEntity {
        @Getter
        @ElementCollection(targetClass = Wall.class, fetch = FetchType.EAGER)
        private final List<Wall> walls = new ArrayList<>();

        // connection from this field to the field with the hashmap key
        @Getter
        @OneToMany(cascade = CascadeType.REMOVE)
        private final Map<UUID, Connection> connections = new HashMap<>();



        public Space(int height, int width) {
            var leftB = new Wall(0, 0, 0, height);
            var rightB = new Wall(width, 0, width, height);
            var downB = new Wall(0, 0, width, 0);
            var upB = new Wall(0, height, width, height);
            getWalls().add(leftB);
            getWalls().add(rightB);
            getWalls().add(downB);
            getWalls().add(upB);
        }
    }