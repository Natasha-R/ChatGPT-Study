package thkoeln.st.st2praktikum.exercise.World.Movable;

import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.World.IHasUUID;
import thkoeln.st.st2praktikum.exercise.World.Point;
import thkoeln.st.st2praktikum.exercise.World.Space.Space;
import thkoeln.st.st2praktikum.exercise.World.Space.SpaceConnection;
import thkoeln.st.st2praktikum.exercise.World.Space.SpaceConnectionCollection;

import java.util.UUID;

public class CleaningDevice implements IHasUUID, IMovable, ITransferable {
    private final UUID uuid = UUID.randomUUID();
    private final String name;
    private final Point position;
    private Space space;
    private final CleaningDeviceService world;

    public CleaningDevice(CleaningDeviceService world, String name) {
        this.world = world;
        this.name = name;
        this.position = new Point(0, 0);
    }

    @Override
    public boolean move(MovementDirectionEnum direction, int steps) {
        Point nextPosition = new Point(0, 0);

        for(int i = 0; i < Math.abs(steps); i++)
        {
            nextPosition.copy(this.position);
            nextPosition.moveOneIn(direction);

            System.out.println(String.format("Next pos would be: %s", nextPosition));


            if(null != this.space) {
                // Wände, Hindernisse etc.
                if (this.space.isVectorColliding(this.position, nextPosition)) {
                    System.out.println(String.format("Movement stopped => collision. POS: %s", this.position));

                    return false;
                }

                // Andere Movables
                MovableCollection movableCollection = this.world.getMovableCollection();

                if(movableCollection.isFieldBlocked(this.space, nextPosition))
                {
                    System.out.println(String.format("There is another movable there. POS: %s", this.position));
                    return false;
                }
            }

            this.position.copy(nextPosition);
        }

        return true;
    }

    private MovementDirectionEnum getOppositeDirection(MovementDirectionEnum direction) {
        switch (direction)
        {
            case no: // oben
                return MovementDirectionEnum.so;
            case so: // unten
                return MovementDirectionEnum.no;
            case ea: // rechts
                return MovementDirectionEnum.we;
            case we: // links
                return MovementDirectionEnum.ea;
        }

        throw new RuntimeException();
    }

    @Override
    public boolean useSpaceConnection() {
        SpaceConnectionCollection spaceConnectionCollection = this.space.getSpaceConnectionCollection();
        SpaceConnection spaceConnection = spaceConnectionCollection.byPoint(this.position);

        if(null == spaceConnection)
        {
            return false;
        }

        this.space = spaceConnection.getDestination();
        System.out.print(String.format("Device copy target dest %s ", spaceConnection.getTargetPosition().toString()));
        this.position.copy(spaceConnection.getTargetPosition());

        return true;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isPlaced() {
        return null != this.space;
    }

    @Override
    public Space getSpace() {
        return this.space;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void setSpace(Space space) {
        this.space = space;
    }
}
