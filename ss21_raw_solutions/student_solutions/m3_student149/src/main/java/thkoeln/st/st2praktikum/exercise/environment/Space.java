package thkoeln.st.st2praktikum.exercise.environment;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.transition.BlockedTransition;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Space {

    @Id
    protected UUID uuid = UUID.randomUUID();

    private int width;
    private int height;

    @OneToMany
    private List<BlockedTransition> blockedTransitions = new ArrayList<>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addBlockedTransitions(Collection<BlockedTransition> blockedTransitions) {
        this.blockedTransitions.addAll(blockedTransitions);
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    protected void setBlockedTransitions(List<BlockedTransition> blockedTransitions) {
        this.blockedTransitions = blockedTransitions;
    }

    public List<BlockedTransition> getBlockedTransitions() {
        return blockedTransitions;
    }

    public boolean isOnSpace(Vector2D vector2D) {
        return vector2D.getX() >= 0 && vector2D.getY() >= 0
                && vector2D.getX() < width && vector2D.getY() < height;
    }

    public UUID getId() {
        return uuid;
    }

    protected void setId(UUID uuid) {
        this.uuid = uuid;
    }
}
