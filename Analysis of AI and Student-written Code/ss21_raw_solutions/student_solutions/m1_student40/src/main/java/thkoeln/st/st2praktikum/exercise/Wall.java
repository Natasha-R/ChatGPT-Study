package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

enum wallType {
    verticalWall,
    horizontalWall
}
public class Wall extends Entity {
    @Getter
    wallType type;

    public Wall(wallType type) {
        this.type = type;
    }
}
